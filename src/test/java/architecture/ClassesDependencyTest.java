package architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ClassesDependencyTest extends BaseArchitectureTest {

    @Test
    public void classes_of_package_service_depends_on_classes_of_package_model() {
        classes().that().resideInAPackage("desapp.grupo.e.service")
                .and().haveSimpleNameEndingWith("Service")
                .should().dependOnClassesThat().resideInAPackage("desapp.*.model")
                .check(classes);
    }

    @Test
    public void classes_of_package_model_not_depends_on_classes_of_other_package() {
        noClasses().that().resideInAPackage("..model..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("desapp.grupo.e.persistence..",
                        "desapp.grupo.e.service..", "desapp.grupo.e.webservice..")
                .check(classes);
    }

}
