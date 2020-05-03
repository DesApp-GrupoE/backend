package desapp.grupo.e.service.persistence.daos;

import desapp.grupo.e.model.builder.CustomerBuilder;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.user.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import desapp.grupo.e.service.persistence.JPAHibernateTest;
import desapp.grupo.e.service.persistence.exception.DataErrorException;

import java.util.Optional;

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

    @Test
    public void addAlertCategoryToCustomerAndSaveShouldHave1Alert() throws DataErrorException {
        Customer customer = CustomerBuilder.aCustomer()
                .anyCustomer()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        customer.addCategoryAlert(categoryAlert);
        this.customerDao.save(customer);

        Optional<Customer> optCustomer = this.customerDao.getById(customer.getId());
        Assertions.assertTrue(optCustomer.isPresent());
        Customer customerFromDb = optCustomer.get();
        Assertions.assertEquals(1, customerFromDb.getCategoryAlerts().size());
    }

    @Test
    public void ifSave2AlertsAndRemove1InCustomerAndSaveShouldHave1Alert() throws DataErrorException {
        Customer customer = CustomerBuilder.aCustomer()
                .anyCustomer()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        customer.addCategoryAlert(categoryAlert);
        CategoryAlert categoryAlert2 = new CategoryAlert(Category.BEBIDAS, 15);
        customer.addCategoryAlert(categoryAlert2);
        this.customerDao.save(customer);

        Optional<Customer> optCustomer = this.customerDao.getById(customer.getId());
        Assertions.assertTrue(optCustomer.isPresent());
        Customer customerFromDb = optCustomer.get();
        Assertions.assertEquals(2, customerFromDb.getCategoryAlerts().size());

        customerFromDb.removeCategoryAlert(categoryAlert);
        this.customerDao.update(customerFromDb);

        Optional<Customer> optCustomer2 = this.customerDao.getById(customer.getId());
        Assertions.assertTrue(optCustomer2.isPresent());
        Customer customerFromDb2 = optCustomer.get();
        Assertions.assertEquals(1, customerFromDb2.getCategoryAlerts().size());
    }

    @Test
    public void ifSave1AlertInCustomerAndAfterUpdateAlertTheCustomerShouldHaveThisAlertUpdated() throws DataErrorException {
        Customer customer = CustomerBuilder.aCustomer()
                .anyCustomer()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        customer.addCategoryAlert(categoryAlert);
        this.customerDao.save(customer);

        categoryAlert.setPercentage(20);
        this.customerDao.update(customer);

        Optional<Customer> optCustomer = this.customerDao.getById(customer.getId());
        Assertions.assertTrue(optCustomer.isPresent());
        Customer customerFromDb = optCustomer.get();
        Assertions.assertEquals(20, customerFromDb.getCategoryAlerts().get(0).getPercentage());
    }
}
