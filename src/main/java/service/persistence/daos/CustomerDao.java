package service.persistence.daos;

import model.user.Customer;
import service.persistence.Dao;
import service.persistence.exception.DataErrorException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
        return null;
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
