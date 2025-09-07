package dev.arol.petclinic.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "dev.arol.petclinic")
public class ArchitectureTest {

    /**
     * No ciclos de dependencia por “slice” de primer nivel
     */
    @ArchTest
    static final ArchRule no_cycles_between_top_level_slices =
            slices().matching("dev.arol.petclinic.(*)..")
                    .should().beFreeOfCycles();
    //<editor-fold desc="CONSTANTS">
    private static final String ROOT = "dev.arol.petclinic..";
    private static final String DOMAIN = "dev.arol.petclinic.domain..";
    private static final String APPLICATION = "dev.arol.petclinic.application..";
    @ArchTest
    static final ArchRule no_entities_in_domain_or_application =
            noClasses().that().resideInAnyPackage(DOMAIN, APPLICATION)
                    .should().beAnnotatedWith(Entity.class);
    private static final String PORT_IN = "dev.arol.petclinic.application.port.in..";
    private static final String PORT_OUT = "dev.arol.petclinic.application.port.out..";
    /**
     * Los puertos son interfaces
     */
    @ArchTest
    static final ArchRule ports_are_interfaces =
            classes().that().resideInAnyPackage(PORT_IN, PORT_OUT)
                    .should().beInterfaces();
    private static final String USECASE = "dev.arol.petclinic.application.usecase..";
    private static final String ADAPTER = "dev.arol.petclinic.adapter..";
    private static final String ADAPTER_IN_WEB = "dev.arol.petclinic.adapter.in.web..";
    /**
     * Controladores solo en adapter.in.web
     */
    @ArchTest
    static final ArchRule controllers_are_only_in_web_adapter =
            classes().that().areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class)
                    .should().resideInAnyPackage(ADAPTER_IN_WEB);
    private static final String ADAPTER_OUT_PERSISTENCE = "dev.arol.petclinic.adapter.out.persistence..";
    //</editor-fold>
    /**
     * Persistence adapter no puede depender de web ni de puertos de entrada
     */
    @ArchTest
    static final ArchRule persistence_does_not_depend_on_web_or_input_ports =
            noClasses().that().resideInAnyPackage(ADAPTER_OUT_PERSISTENCE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            ADAPTER_IN_WEB, PORT_IN
                    );
    /**
     * @Entity solo en el adaptador de persistencia; nunca en domain/application
     */
    @ArchTest
    static final ArchRule entities_are_only_in_persistence_adapter =
            classes().that().areAnnotatedWith(Entity.class)
                    .should().resideInAnyPackage(ADAPTER_OUT_PERSISTENCE);
    /**
     * @Repository solo en adapter.out.persistence (Spring Data / DAOs)
     */
    @ArchTest
    static final ArchRule repositories_are_only_in_persistence_adapter =
            classes().that().areAnnotatedWith(Repository.class)
                    .should().resideInAnyPackage(ADAPTER_OUT_PERSISTENCE);
    private static final String CONFIG = "dev.arol.petclinic.config..";
    /**
     * Web adapter no puede depender de persistence ni de puertos de salida
     */
    @ArchTest
    static final ArchRule application_does_not_depend_on_adapters_or_config =
            noClasses().that().resideInAnyPackage(APPLICATION)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            ADAPTER, CONFIG
                    );
    private static final String SPRING = "..org.springframework..";
    private static final String SPRING_DATA = "..org.springframework.data..";
    private static final String JPA = "..jakarta.persistence..";
    /**
     * Dominio totalmente aislado de capas externas y de frameworks
     */
    @ArchTest
    static final ArchRule domain_is_pure =
            noClasses().that().resideInAnyPackage(DOMAIN)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            ADAPTER, APPLICATION, CONFIG, SPRING, SPRING_DATA, JPA
                    );

}
