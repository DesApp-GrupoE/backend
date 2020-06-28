package architecture;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

public class TransactionalTest extends BaseArchitectureTest {

    @Test
    public void noneClassOfPersistencePackageShouldUseTransactionalAnnotation() {
        noClasses().that().resideInAPackage("desapp.grupo.e.persistence..")
                .should().beAnnotatedWith(Transactional.class)
                .check(classes);
    }

    @Test
    public void noneMethodOfClassesInPersistencePackageShouldUseTransactionalAnnotation() {
        noMethods().that()
                .areDeclaredInClassesThat().resideInAPackage("desapp.grupo.e.persistence..")
                .should().beAnnotatedWith(Transactional.class)
                .check(classes);
    }
}
