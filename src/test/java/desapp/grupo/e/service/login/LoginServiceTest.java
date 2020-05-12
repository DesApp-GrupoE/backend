package desapp.grupo.e.service.login;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import desapp.grupo.e.persistence.exception.UniqueClassException;

import java.util.Optional;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    private LoginService loginService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(userRepository);
    }

    @Test
    public void signUpCreateNewCustomer() throws UniqueClassException {
        Long expectedId = 1L;

        User user = new UserBuilder().anyUser().build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        doAnswer(invocation -> {
            ReflectionTestUtils.setField((User) invocation.getArgument(0), "id", expectedId);
            return null;
        }).when(userRepository).save(user);

        loginService.signUp(user);

        Assertions.assertEquals(user.getId(), expectedId);
    }

    @Test
    public void signUpCreateNewCustomerWithEmailExistentThrowUniqueClassException() {
        User user = new UserBuilder().anyUser().build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(UniqueClassException.class, () -> loginService.signUp(user));
    }
}
