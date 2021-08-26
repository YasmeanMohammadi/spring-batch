package com.polixis.task1;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.polixis.task1");

        noClasses()
            .that()
                .resideInAnyPackage("com.polixis.task1.service..")
            .or()
                .resideInAnyPackage("com.polixis.task1.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.polixis.task1.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
