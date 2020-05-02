package desapp.grupo.e.service.persistence.daos;

import desapp.grupo.e.model.builder.CustomerBuilder;
import desapp.grupo.e.model.user.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import desapp.grupo.e.service.persistence.JPAHibernateTest;
import desapp.grupo.e.service.persistence.exception.DataErrorException;

public class CustomerDAOTest extends JPAHibernateTest {

    private CustomerDao customerDao;

    @BeforeEach
    public void setUp() {
        this.customerDao = new CustomerDao(entityManager);
    }

    @AfterEach
    public void tearDown() throws DataErrorException {
        this.customerDao.deleteAll();
    }

    @Test
    public void existCustomerWithEmail() throws DataErrorException {
        Customer customer = CustomerBuilder.aCustomer()
                .anyCustomer() // Para setear todos los datos
                .withEmail("ariel.ramirez@test.test")
                .build();
        this.customerDao.save(customer);

        Boolean exists = this.customerDao.existCustomerWithEmail("ariel.ramirez@test.test");
        Assertions.assertTrue(exists);
    }
}
