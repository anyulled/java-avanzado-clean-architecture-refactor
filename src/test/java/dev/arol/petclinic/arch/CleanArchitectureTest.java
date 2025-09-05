package dev.arol.petclinic.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "dev.arol.petclinic")

public class CleanArchitectureTest {
    private static final String DOMAIN = "dev.arol.petclinic.domain..";
    private static final String APPLICATION = "dev.arol.petclinic.application..";
    /**
     * Domain y Application no usan estereotipos Spring ni @Entity
     */
    @ArchTest
    static final ArchRule no_spring_stereotypes_or_entity_in_domain_application =
            noClasses().that().resideInAnyPackage(DOMAIN, APPLICATION)
                    .should().beAnnotatedWith(Service.class)
                    .orShould().beAnnotatedWith(Component.class)
                    .orShould().beAnnotatedWith(Repository.class)
                    .orShould().beAnnotatedWith(Controller.class)
                    .orShould().beAnnotatedWith(RestController.class)
                    .orShould().beAnnotatedWith(Configuration.class)
                    .orShould().beAnnotatedWith(Entity.class);
    private static final String SPRING = "..org.springframework..";
    private static final String SPRING_DATA = "..org.springframework.data..";
    private static final String JPA = "..jakarta.persistence..";
    /**
     * Domain y Application no dependen de Spring/JPA en absoluto
     */
    @ArchTest
    static final ArchRule domain_application_do_not_depend_on_spring_jpa =
            noClasses().that().resideInAnyPackage(DOMAIN, APPLICATION)
                    .should().dependOnClassesThat().resideInAnyPackage(SPRING, SPRING_DATA, JPA);
}

