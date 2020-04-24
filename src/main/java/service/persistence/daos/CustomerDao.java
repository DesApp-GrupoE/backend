package service.persistence.daos;

import model.user.Customer;
import org.springframework.stereotype.Service;
import service.persistence.Dao;
import service.persistence.exception.DataErrorException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class CustomerDao implements Dao<Customer> {

    private EntityManager entityManager;

    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return Optional.ofNullable(this.entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Customer e");
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.persist(customer));
    }

    @Override
    public void update(Customer customer, String[] params) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.merge(customer));
    }

    @Override
    public void delete(Customer customer) throws DataErrorException {
        executeInsideTransaction(entityManager -> entityManager.remove(customer));
    }

    public Boolean existCustomerWithEmail(String email) {
        String hql = "FROM Customer WHERE email = :email";
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        return existsByQuery(hql, params, Customer.class);
    }

    public <T> boolean existsByQuery(String queryString, HashMap<String, Object> params, Class<T> clazz) {
        T cast = getSingleResponseByQueryWithParams(queryString, params, clazz);
        return cast != null;
    }

    private <T> T getSingleResponseByQueryWithParams(String queryString, HashMap<String, Object> params, Class<T> clazz) {
        Query query = createQueryWithParams(queryString, params);
        try {
            return clazz.cast(query.getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    private Query createQueryWithParams(String queryString, HashMap<String, Object> params) {
        Query query = this.entityManager.createQuery(queryString);
        for(Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        return query;
    }


    private void executeInsideTransaction(Consumer<EntityManager> action) throws DataErrorException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new DataErrorException(e.getMessage());
        }
    }
}
