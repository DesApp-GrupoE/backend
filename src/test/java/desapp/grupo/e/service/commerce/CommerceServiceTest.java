package desapp.grupo.e.service.commerce;

import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import desapp.grupo.e.persistence.exception.CommerceDuplicatedException;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommerceServiceTest {

    private UserRepository userRepository;
    private CommerceRepository commerceRepository;
    private CommerceService commerceService;

    @BeforeEach
    public void setUp() {
        this.userRepository = mock(UserRepository.class);
        this.commerceRepository = mock(CommerceRepository.class);
        this.commerceService = new CommerceService(userRepository, commerceRepository);
    }

    @Test
    public void createCommerceShouldNoThrowException() {
        Long userId = 1L;
        User user = UserBuilder.aUser().anyUser().withId(userId).build();
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().build();
        when(commerceRepository.existCommerceInDatabase(anyString())).thenReturn(Boolean.FALSE);
        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> commerceService.save(userId, commerce));
    }

    @Test
    public void createCommerceWithNameExistentShouldThrowCommerceDuplicatedException() {
        Long userId = 1L;
        String commerceName = "Commerce Test";
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().withName(commerceName).build();
        when(commerceRepository.existCommerceInDatabase(eq(commerceName))).thenReturn(Boolean.TRUE);

        Assertions.assertThrows(CommerceDuplicatedException.class, () -> commerceService.save(userId, commerce));
    }

    @Test
    public void createCommerceAndUserNotExistsShouldThrowResourceNotFoundException() {
        Long userId = 1L;
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().build();
        when(commerceRepository.existCommerceInDatabase(anyString())).thenReturn(Boolean.FALSE);
        when(userRepository.findById(eq(userId))).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commerceService.save(userId, commerce));
    }

    @Test
    public void getCommerceByUserId() {
        Long userId = 1L;
        Long commerceId = 2L;
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().withId(commerceId).build();
        when(commerceRepository.findByUser(userId)).thenReturn(commerce);

        Commerce commerceDb = commerceService.getCommerceByUser(userId);

        Assertions.assertEquals(commerceId, commerceDb.getId());
    }

    @Test
    public void getCommerceByUserIdInexistentShouldReturnNull() {
        Long userId = 1L;
        when(commerceRepository.findByUser(userId)).thenReturn(null);

        Assertions.assertNull(commerceService.getCommerceByUser(userId));
    }

    @Test
    public void deleteCommerceNoThrowException() {
        Long userId = 1L;
        Long commerceId = 1L;

        Assertions.assertDoesNotThrow(() -> commerceService.removeById(userId, commerceId));
    }

    @Test
    public void deleteCommerceWhenNotExistNoThrowException() {
        Long userId = 1L;
        Long commerceId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(commerceRepository).deleteById(commerceId);

        Assertions.assertDoesNotThrow(() -> commerceService.removeById(userId, commerceId));
    }
}
