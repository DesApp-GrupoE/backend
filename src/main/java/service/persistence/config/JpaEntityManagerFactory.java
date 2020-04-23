package service.persistence.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManagerFactory {

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("ComprandoEnCasa");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
