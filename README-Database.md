# PetClinic Database Configuration Guide

This PetClinic application supports multiple databases with easy switching between H2 (in-memory) and PostgreSQL. The same repository interfaces work with both databases without any code changes.

## 🗄️ Supported Databases

### H2 Database (Default)
- **Type**: In-memory database
- **Best for**: Development, testing, quick demos
- **Data**: Resets on application restart
- **Web Console**: Available at `http://localhost:8080/h2-console`

### PostgreSQL
- **Type**: Production-grade relational database  
- **Best for**: Production-like testing, persistent data
- **Data**: Persists between application restarts
- **Admin Tool**: PgAdmin available via Docker

## 🚀 Quick Start

### Option 1: H2 Database (Default)
```bash
# Run with H2 (default profile)
mvn spring-boot:run

# Or explicitly specify H2 profile
mvn spring-boot:run -Dspring.profiles.active=h2
```

### Option 2: PostgreSQL Database
```bash
# 1. Start PostgreSQL with Docker
docker-compose up -d postgres

# 2. Run application with PostgreSQL profile
mvn spring-boot:run -Dspring.profiles.active=postgres
```

## 🔧 Database Switching Methods

### 1. Command Line (Maven)
```bash
# H2 Database
mvn spring-boot:run -Dspring.profiles.active=h2

# PostgreSQL Database  
mvn spring-boot:run -Dspring.profiles.active=postgres
```

### 2. Environment Variable
```bash
# Set environment variable
export SPRING_PROFILES_ACTIVE=postgres
mvn spring-boot:run

# Windows
set SPRING_PROFILES_ACTIVE=postgres
mvn spring-boot:run
```

### 3. IDE Configuration
In your IDE run configuration, set:
- **Program arguments**: `--spring.profiles.active=postgres`
- **Environment variable**: `SPRING_PROFILES_ACTIVE=postgres`

### 4. JAR Execution
```bash
# Build the JAR
mvn clean package

# Run with specific profile
java -jar target/petclinic-simple-1.0.0.jar --spring.profiles.active=postgres
```

## 🐘 PostgreSQL Setup

### Using Docker Compose (Recommended)
```bash
# Start PostgreSQL and PgAdmin
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs postgres

# Stop services
docker-compose down
```

**PostgreSQL Connection Details:**
- **Host**: localhost
- **Port**: 5432
- **Database**: petclinic
- **Username**: petclinic
- **Password**: petclinic

**PgAdmin Web Interface:**
- **URL**: http://localhost:8081
- **Email**: admin@petclinic.com
- **Password**: admin

### Manual PostgreSQL Installation
If you prefer to install PostgreSQL manually:

1. Install PostgreSQL on your system
2. Create database and user:
```sql
CREATE DATABASE petclinic;
CREATE USER petclinic WITH PASSWORD 'petclinic';
GRANT ALL PRIVILEGES ON DATABASE petclinic TO petclinic;
```
3. Update connection details in `application-postgres.properties` if needed

## 📊 Sample Data

Both databases are automatically populated with sample data:

### H2 Sample Data (`import-h2.sql`)
- 8 pets with various species
- 8 appointments linked to pets

### PostgreSQL Sample Data (`import-postgres.sql`)
- 10 pets with various species  
- 11 appointments linked to pets

## 🔍 Database Access Tools

### H2 Console (H2 Profile)
1. Start application with H2 profile
2. Visit: http://localhost:8080/h2-console
3. Use connection settings:
   - **JDBC URL**: `jdbc:h2:mem:petclinic`
   - **User Name**: `sa`
   - **Password**: (leave empty)

### PgAdmin (PostgreSQL Profile)
1. Start Docker services: `docker-compose up -d`
2. Visit: http://localhost:8081
3. Login with admin credentials
4. Add server connection with PostgreSQL details above

## 🛠️ Configuration Files

### Main Configuration (`application.properties`)
```properties
# Default profile (change this to switch default database)
spring.profiles.active=h2

# Shared configuration for both databases
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### H2 Configuration (`application-h2.properties`)
```properties
spring.config.activate.on-profile=h2
spring.datasource.url=jdbc:h2:mem:petclinic
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### PostgreSQL Configuration (`application-postgres.properties`)
```properties
spring.config.activate.on-profile=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/petclinic
spring.datasource.username=petclinic
spring.jpa.hibernate.ddl-auto=create-drop
```

## 🔄 Profile-Based Data Loading

The application automatically loads appropriate sample data based on the active profile:
- **H2 Profile**: Loads `import-h2.sql`
- **PostgreSQL Profile**: Loads `import-postgres.sql`

## 🧪 Testing with Different Databases

### Postman Collection
The existing Postman collection works with both databases:
```bash
# Test with H2
mvn spring-boot:run -Dspring.profiles.active=h2
# Run Postman collection

# Test with PostgreSQL
docker-compose up -d postgres
mvn spring-boot:run -Dspring.profiles.active=postgres  
# Run Postman collection
```

### API Endpoints
All REST endpoints work identically regardless of database:
- `GET /pets` - List all pets
- `POST /pets` - Create new pet
- `GET /appointments` - List all appointments
- `POST /appointments` - Create new appointment

## 🚨 Troubleshooting

### H2 Database Issues
```bash
# Check H2 console access
curl http://localhost:8080/h2-console

# Verify application is running with H2 profile
curl http://localhost:8080/actuator/env | grep "spring.profiles.active"
```

### PostgreSQL Connection Issues
```bash
# Check PostgreSQL container is running
docker-compose ps

# Test PostgreSQL connection
docker-compose exec postgres psql -U petclinic -d petclinic -c "\\dt"

# Check application logs for connection errors
mvn spring-boot:run -Dspring.profiles.active=postgres
```

### Profile Not Loading
```bash
# Verify active profile
curl http://localhost:8080/actuator/env | grep profiles

# Check configuration files exist
ls -la src/main/resources/application-*.properties
```

## 📈 Production Recommendations

### For Development
- Use **H2** for quick testing and demos
- No setup required, immediate startup

### For Integration Testing  
- Use **PostgreSQL** with Docker
- Closer to production environment
- Persistent data for longer test cycles

### For Production
- Use external PostgreSQL instance
- Update `application-postgres.properties` with production credentials
- Consider using environment variables for sensitive data:
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-server:5432/petclinic
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=secure_password
```

## 🔧 Advanced Configuration

### Custom Database Profiles
Create additional profiles by adding new configuration files:
```bash
# application-mysql.properties for MySQL support
# application-oracle.properties for Oracle support
```

### Environment-Specific Profiles
```bash
# application-dev.properties (development)
# application-staging.properties (staging) 
# application-prod.properties (production)
```

---

**Happy Database Switching! 🔄**