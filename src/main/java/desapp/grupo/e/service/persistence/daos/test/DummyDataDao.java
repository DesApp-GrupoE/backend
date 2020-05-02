package desapp.grupo.e.service.persistence.daos.test;

import desapp.grupo.e.model.test.DummyData;
import desapp.grupo.e.service.persistence.AbstractDao;

import javax.persistence.EntityManager;

public class DummyDataDao extends AbstractDao<DummyData> {

    public DummyDataDao(EntityManager entityManager) {
        super(entityManager, DummyData.class);
    }
}
