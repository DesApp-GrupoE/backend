package desapp.grupo.e.service.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAHibernateTest {

    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;

    @BeforeAll
    public static void setupEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("ComprandoEnCasa-test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void tearDownEntityManager(){
        entityManager.clear();
        entityManager.close();
        entityManagerFactory.close();
    }
}
