package desapp.grupo.e.service.login;

import desapp.grupo.e.model.builder.UserBuilder;
import desapp.grupo.e.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import desapp.grupo.e.persistence.daos.UserDao;
import desapp.grupo.e.persistence.exception.DataErrorException;
import desapp.grupo.e.persistence.exception.UniqueClassException;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Test
    public void signUpCreateNewCustomer() throws UniqueClassException, DataErrorException {
        UserDao userDao = mock(UserDao.class);
        LoginService loginService = new LoginService(userDao);
        Long expectedId = 1L;

        User user = new UserBuilder().anyUser().build();
        when(userDao.existUserWithEmail(user.getEmail())).thenReturn(false);

        doAnswer(invocation -> {
            ReflectionTestUtils.setField((User) invocation.getArgument(0), "id", expectedId);
            return null;
        }).when(userDao).save(user);

        loginService.signUp(user);

        Assertions.assertEquals(user.getId(), expectedId);
    }

    @Test
    public void signUpCreateNewCustomerWithEmailExistentThrowUniqueClassException() {
        UserDao userDao = mock(UserDao.class);
        LoginService loginService = new LoginService(userDao);
        User user = new UserBuilder().anyUser().build();

        when(userDao.existUserWithEmail(user.getEmail())).thenReturn(true);

        Assertions.assertThrows(UniqueClassException.class, () -> loginService.signUp(user));
    }
}
