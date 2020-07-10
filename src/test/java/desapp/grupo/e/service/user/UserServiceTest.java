package desapp.grupo.e.service.user;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;
    private AuthService authService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authService = mock(AuthService.class);
        userService = new UserService(userRepository, authService);
    }

    @Test
    public void getUserById() {
        Long userId = 1L;
        User userMock = UserBuilder.aUser().anyUser().withId(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));

        User userFromDb = userService.getUserById(userId);

        Assertions.assertNotNull(userFromDb);
    }

    @Test
    public void getUserByIdInexistentThrowException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

//    @Test
//    public void getUserByToken() {
//        User userMock = UserBuilder.aUser().anyUser().build();
//        when(authService.getEmailByToken(anyString())).thenReturn("test@test.test");
//        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userMock));
//
//        User user = userService.getUserByToken("token");
//
//        Assertions.assertNotNull(user);
//    }
//
//    @Test
//    public void getUserByTokenAnEmailInexistentThrowException() {
//        when(authService.getEmailByToken(anyString())).thenReturn("test@test.test");
//        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUserByToken("token"));
//    }

}
