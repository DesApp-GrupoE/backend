package desapp.grupo.e.persistence;

import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.persistence.exception.DataErrorException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractDao<T> implements Dao<T> {

    protected EntityManager entityManager;
    private Class<T> clazz;

    public AbstractDao(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    @Override
    public List<T> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e");
        return query.getResultList();
    }

    @Override
    public Optional<T> getById(Long id) {
        return Optional.ofNullable(this.entityManager.find(clazz, id));
    }

    @Override
    public void save(T objectData) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.persist(objectData));
    }

    @Override
    public void update(T objectData) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.merge(objectData));
    }

    @Override
    public void delete(T objectData) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.remove(objectData));
    }

    @Override
    public void deleteAll() throws DataErrorException {
        String hql = "delete from " + clazz.getName();
        executeQueryInsideTransaction(hql, new HashMap<>());
    }

    protected void executeInsideTransaction(Consumer<EntityManager> action) throws DataErrorException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            Log.debug(e.getMessage());
            throw new DataErrorException(e.getMessage());
        }
    }


    protected void executeQueryInsideTransaction(String hql, HashMap<String, Object> params) throws DataErrorException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Query query = createQueryWithParams(hql, params);
            query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new DataErrorException(e.getMessage());
        }
    }

    protected <T> boolean existsByQuery(String queryString, HashMap<String, Object> params, Class<T> clazz) {
        T cast = getSingleResponseByQueryWithParams(queryString, params, clazz);
        return cast != null;
    }

    protected <T> T getSingleResponseByQueryWithParams(String queryString, HashMap<String, Object> params, Class<T> clazz) {
        Query query = createQueryWithParams(queryString, params);
        try {
            return clazz.cast(query.getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    protected Query createQueryWithParams(String queryString, HashMap<String, Object> params) {
        Query query = this.entityManager.createQuery(queryString);
        for(Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        return query;
    }

}
