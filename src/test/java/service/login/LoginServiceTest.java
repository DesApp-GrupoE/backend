package service.login;

import model.builder.CustomerBuilder;
import model.user.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import service.persistence.daos.CustomerDao;
import service.persistence.exception.DataErrorException;
import service.persistence.exception.UniqueClassException;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Test
    public void signUpCreateNewCustomer() throws UniqueClassException, DataErrorException {
        CustomerDao customerDao = mock(CustomerDao.class);
        LoginService loginService = new LoginService(customerDao);
        Long expectedId = 1L;

        Customer customer = new CustomerBuilder().anyCustomer().build();
        when(customerDao.existCustomerWithEmail(customer.getEmail())).thenReturn(false);

        doAnswer(invocation -> {
            ReflectionTestUtils.setField((Customer) invocation.getArgument(0), "id", expectedId);
            return null;
        }).when(customerDao).save(customer);

        loginService.signUp(customer);

        Assertions.assertEquals(customer.getId(), expectedId);
    }

    @Test
    public void signUpCreateNewCustomerWithEmailExistentThrowUniqueClassException() {
        CustomerDao customerDao = mock(CustomerDao.class);
        LoginService loginService = new LoginService(customerDao);
        Customer customer = new CustomerBuilder().anyCustomer().build();

        when(customerDao.existCustomerWithEmail(customer.getEmail())).thenReturn(true);

        Assertions.assertThrows(UniqueClassException.class, () -> loginService.signUp(customer));
    }
}
