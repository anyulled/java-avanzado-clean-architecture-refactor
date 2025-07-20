# Ejercicio: Refactorización a Arquitectura Hexagonal

## 🎯 Objetivo del Ejercicio

Este ejercicio te guiará paso a paso para refactorizar una aplicación **Spring Boot** desde una **arquitectura por capas tradicional** hacia una **arquitectura hexagonal**. El objetivo es comprender las ventajas de separar la lógica de dominio de los detalles técnicos de infraestructura.

## 🏗️ Arquitectura Actual vs Objetivo

### 📊 Arquitectura Actual (Por Capas)
```
Controller → Service → Repository → Database
```
- Dependencias fluyen hacia abajo
- Servicios conocen repositorios JPA
- Lógica de dominio mezclada con infraestructura

### 🎯 Arquitectura Objetivo (Hexagonal)
```
Web Adapter → Use Case ← Persistence Adapter
     ↓           ↓              ↓
   HTTP       Domain        Database
```
- Dominio en el centro, independiente
- Adaptadores implementan puertos
- Lógica de dominio pura y testeable

## 🚀 Configuración Inicial

### Ejecutar la Aplicación
Puedes ejecutar la aplicación con cualquiera de estos perfiles:

```bash
# Con base de datos H2 (en memoria)
mvn spring-boot:run -Dspring.profiles.active=h2

# Con base de datos PostgreSQL (requiere Docker)
docker-compose up -d
mvn spring-boot:run -Dspring.profiles.active=postgres

# Con repositorio en memoria (sin BD)
mvn spring-boot:run -Dspring.profiles.active=inmemory
```

### Validar Funcionalidad
Para verificar que la aplicación sigue funcionando después de cada paso:

```bash
./postman-tests.sh
```

Este script ejecuta tests end-to-end que validan todos los endpoints de la API.

## 📋 Pasos de Refactorización

### 🎯 Paso 1: Crear la Estructura de Paquetes

Crea la siguiente estructura de paquetes:

```
src/main/java/dev/arol/petclinic/
├── domain/
│   └── model/              # Entidades de dominio puras
├── application/
│   ├── port/
│   │   ├── in/             # Puertos de entrada (casos de uso)
│   │   └── out/            # Puertos de salida (repositorios)
│   └── usecase/            # Implementación de casos de uso
└── adapter/
    ├── in/
    │   └── web/            # Controladores REST
    └── out/
        └── persistence/    # Implementaciones de repositorios
```

**🤔 ¿Por qué esta estructura?**
- **domain**: Contiene la lógica de negocio pura, sin dependencias externas
- **application**: Define contratos (puertos) e implementa casos de uso
- **adapter**: Implementa los detalles técnicos que conectan con el exterior

### 🏛️ Paso 2: Crear el Dominio

#### 2.1 Mover Entidades al Dominio

Mueve `Pet.java` y `Appointment.java` al paquete `domain.model` y **limpia las anotaciones JPA**:

```java
// domain/model/Pet.java
public class Pet {
    private Long id;
    private String name;
    private String species;
    private String ownerName;
    
    // Mantener solo validaciones de dominio
    // Eliminar @Entity, @Table, @Column, etc.
}
```

**🤔 ¿Por qué eliminar anotaciones JPA?**
Las entidades de dominio deben ser **independientes de cualquier tecnología**. JPA es un detalle de implementación que no debe contaminar el dominio.

#### 2.2 Agregar Lógica de Dominio

Añade métodos de validación y comportamiento al dominio:

```java
public class Pet {
    // ... campos existentes
    
    public boolean isValidForAppointment() {
        return name != null && !name.trim().isEmpty() 
               && species != null && !species.trim().isEmpty();
    }
    
    public void validateForCreation() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie de la mascota es obligatoria");
        }
    }
}
```

### 🔌 Paso 3: Definir Puertos

#### 3.1 Puertos de Entrada (Input Ports)

En `application.port.in`, define los casos de uso:

```java
// application/port/in/CreatePetUseCase.java
public interface CreatePetUseCase {
    Pet createPet(Pet pet);
}

// application/port/in/GetPetsUseCase.java
public interface GetPetsUseCase {
    List<Pet> getAllPets();
}

// application/port/in/PetExistsUseCase.java
public interface PetExistsUseCase {
    boolean petExists(Long petId);
}
```

#### 3.2 Puertos de Salida (Output Ports)

En `application.port.out`, define las necesidades del dominio:

```java
// application/port/out/PetRepository.java
public interface PetRepository {
    Pet save(Pet pet);
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
    boolean existsById(Long id);
}
```

**🤔 ¿Diferencia entre puertos de entrada y salida?**
- **Entrada**: Lo que el dominio **ofrece** al mundo exterior (casos de uso)
- **Salida**: Lo que el dominio **necesita** del mundo exterior (repositorios, servicios externos)

### ⚙️ Paso 4: Implementar Casos de Uso

En `application.usecase`, implementa la lógica de aplicación:

```java
// application/usecase/PetUseCaseImpl.java
@Service
public class PetUseCaseImpl implements CreatePetUseCase, GetPetsUseCase, PetExistsUseCase {
    
    private final PetRepository petRepository;
    
    public PetUseCaseImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }
    
    @Override
    public Pet createPet(Pet pet) {
        pet.validateForCreation(); // Lógica de dominio
        return petRepository.save(pet);
    }
    
    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    
    @Override
    public boolean petExists(Long petId) {
        return petRepository.existsById(petId);
    }
}
```

**🤔 ¿Por qué separar casos de uso?**
Cada caso de uso representa una **operación específica** que el sistema puede realizar. Esto facilita el testing y el mantenimiento.

### 🌐 Paso 5: Crear Adaptador Web

En `adapter.in.web`, crea los controladores:

```java
// adapter/in/web/PetController.java
@RestController
@RequestMapping("/pets")
public class PetController {
    
    private final CreatePetUseCase createPetUseCase;
    private final GetPetsUseCase getPetsUseCase;
    
    public PetController(CreatePetUseCase createPetUseCase, 
                        GetPetsUseCase getPetsUseCase) {
        this.createPetUseCase = createPetUseCase;
        this.getPetsUseCase = getPetsUseCase;
    }
    
    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = createPetUseCase.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }
    
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = getPetsUseCase.getAllPets();
        return ResponseEntity.ok(pets);
    }
}
```

**🤔 ¿Cambios respecto al controlador original?**
- Depende de **puertos** (interfaces), no de servicios concretos
- Utiliza **casos de uso específicos**, no un servicio general
- Más **explícito** sobre qué operaciones puede realizar

### 💾 Paso 6: Crear Adaptadores de Persistencia

#### 6.1 Adaptador JPA

En `adapter.out.persistence`, crea el adaptador JPA:

```java
// adapter/out/persistence/Pet.java (Entidad JPA)
@Entity
@Table(name = "pets")
public class PetJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String species;
    
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    
    // Constructores, getters, setters
    
    // Método para convertir a entidad de dominio
    public Pet toDomain() {
        return new Pet(id, name, species, ownerName);
    }
    
    // Método para crear desde entidad de dominio
    public static PetJpaEntity fromDomain(Pet pet) {
        return new PetJpaEntity(pet.getId(), pet.getName(), 
                               pet.getSpecies(), pet.getOwnerName());
    }
}

// adapter/out/persistence/PetRepositoryJpa.java (Repositorio Spring Data)
public interface PetRepositoryJpa extends JpaRepository<PetJpaEntity, Long> {
}

// adapter/out/persistence/PetRepositoryAdapter.java (Adaptador)
@Repository
@Profile({"h2", "postgres"})
public class PetRepositoryAdapter implements PetRepository {
    
    private final PetRepositoryJpa petRepositoryJpa;
    
    public PetRepositoryAdapter(PetRepositoryJpa petRepositoryJpa) {
        this.petRepositoryJpa = petRepositoryJpa;
    }
    
    @Override
    public Pet save(Pet pet) {
        PetJpaEntity entity = PetJpaEntity.fromDomain(pet);
        PetJpaEntity saved = petRepositoryJpa.save(entity);
        return saved.toDomain();
    }
    
    @Override
    public List<Pet> findAll() {
        return petRepositoryJpa.findAll()
                .stream()
                .map(PetJpaEntity::toDomain)
                .toList();
    }
    
    // ... otros métodos
}
```

#### 6.2 Adaptador In-Memory

Actualiza el adaptador in-memory para implementar el puerto:

```java
// adapter/out/persistence/PetRepositoryInMemory.java
@Repository
@Profile("inmemory")
public class PetRepositoryInMemory implements PetRepository {
    
    private final ConcurrentHashMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet = new Pet(idGenerator.getAndIncrement(), 
                         pet.getName(), pet.getSpecies(), pet.getOwnerName());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }
    
    // ... otros métodos
}
```

**🤔 ¿Ventajas de los adaptadores?**
- **Separación**: Lógica de persistencia separada del dominio
- **Intercambiables**: Puedes cambiar de JPA a MongoDB sin afectar el dominio
- **Testeable**: Puedes usar adaptadores mock para testing

### 🔄 Paso 7: Repetir para Appointments

Repite los pasos 2-6 para la entidad `Appointment`:

1. Mover `Appointment` a `domain.model`
2. Crear puertos de entrada y salida para citas
3. Implementar casos de uso de citas
4. Crear controlador de citas
5. Crear adaptadores de persistencia para citas

### 🗑️ Paso 8: Limpiar Código Antiguo

Una vez que hayas migrado todas las entidades:

1. **Elimina** los paquetes antiguos: `entity`, `service`, `repository`
2. **Elimina** las interfaces `IPetRepository`, `IAppointmentRepository`
3. **Actualiza** las importaciones en toda la aplicación
4. **Verifica** que no queden referencias a la estructura antigua

### ✅ Paso 9: Validar Funcionalidad

Después de cada paso importante, ejecuta:

```bash
./postman-tests.sh
```

Si todos los tests pasan, ¡la refactorización es exitosa!

## 🎯 Resultado Final

### Estructura Final
```
src/main/java/dev/arol/petclinic/
├── domain/
│   └── model/
│       ├── Pet.java
│       └── Appointment.java
├── application/
│   ├── port/
│   │   ├── in/
│   │   │   ├── CreatePetUseCase.java
│   │   │   ├── GetPetsUseCase.java
│   │   │   └── ...
│   │   └── out/
│   │       ├── PetRepository.java
│   │       └── AppointmentRepository.java
│   └── usecase/
│       ├── PetUseCaseImpl.java
│       └── AppointmentUseCaseImpl.java
└── adapter/
    ├── in/
    │   └── web/
    │       ├── PetController.java
    │       └── AppointmentController.java
    └── out/
        └── persistence/
            ├── PetJpaEntity.java
            ├── PetRepositoryJpa.java
            ├── PetRepositoryAdapter.java
            └── ...
```

### Flujo de Datos
```
HTTP Request → Web Adapter → Use Case → Domain Model → Repository Port → Persistence Adapter → Database
```

## 🏆 Beneficios Conseguidos

### ✅ **Independencia Tecnológica**
- El dominio no conoce Spring, JPA, HTTP
- Puedes cambiar tecnologías sin afectar la lógica de negocio

### ✅ **Testabilidad Mejorada**
- Casos de uso testeable sin infraestructura
- Mocks simples de implementar

### ✅ **Mantenibilidad**
- Responsabilidades claras y separadas
- Fácil localizar y modificar funcionalidades

### ✅ **Evolutivo**
- Fácil agregar nuevos casos de uso
- Nuevos adaptadores sin modificar el core

## 🤔 Reflexiones

1. **¿Qué diferencias observas en la testabilidad del código?**
2. **¿Cómo cambiaría añadir un nuevo endpoint vs la versión anterior?**
3. **¿Qué pasaría si quisiéramos cambiar de REST a GraphQL?**
4. **¿Es más fácil o más difícil entender el flujo de la aplicación?**

## 📚 Conceptos Clave

- **Hexagonal**: El dominio está en el centro, aislado del exterior
- **Puerto**: Contrato/interfaz que define cómo interactuar con el dominio
- **Adaptador**: Implementación técnica que conecta puertos con tecnologías
- **Caso de Uso**: Operación específica que el sistema puede realizar
- **Inversión de Dependencias**: La infraestructura depende del dominio, no al revés

¡Felicidades por completar la refactorización! 🎉