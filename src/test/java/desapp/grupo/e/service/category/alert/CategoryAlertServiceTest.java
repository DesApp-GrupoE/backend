package desapp.grupo.e.service.category.alert;

import desapp.grupo.e.ApplicationJpaRepositoryTest;
import desapp.grupo.e.model.builder.product.CategoryAlertBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.category.alert.CategoryAlertRepository;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationJpaRepositoryTest.class)
@Transactional
public class CategoryAlertServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryAlertRepository categoryAlertRepository;
    private CategoryAlertService categoryAlertService;
    private User user;

    @BeforeEach

    public void setUp() {
        this.categoryAlertService = new CategoryAlertService(userRepository, categoryAlertRepository);
        User userToSave = UserBuilder.aUser().anyUser().build();
        this.userRepository.save(userToSave);
        user = this.userRepository.findByEmail(userToSave.getEmail()).orElseGet(null);
    }

    @AfterEach
    public void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    public void saveACategoryAlertShouldReturnThisWithId() {
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert()
                .withCategory(Category.ALMACEN)
                .withPercentage(10).build();

        CategoryAlert categoryAlertSaved = categoryAlertService.save(user.getId(), categoryAlert);

        Assertions.assertNotNull(categoryAlertSaved);
        Assertions.assertNotNull(categoryAlertSaved.getId());
    }

    @Test
    public void saveACategoryAlertToUserIdInexistentShouldThrowResourceNotFoundException() {
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert()
                .withCategory(Category.ALMACEN)
                .withPercentage(10).build();

        Assertions.assertThrows(ResourceNotFoundException.class, ()-> categoryAlertService.save(0, categoryAlert));
    }

    @Test
    public void getCategoryAlertsFromUser() {
        CategoryAlert categoryAlert1 = CategoryAlertBuilder.aCategoryAlert().withCategory(Category.ALMACEN).withPercentage(10).build();
        CategoryAlert categoryAlert2 = CategoryAlertBuilder.aCategoryAlert().withCategory(Category.BEBIDAS).withPercentage(10).build();
        user.addCategoryAlert(categoryAlert1);
        user.addCategoryAlert(categoryAlert2);
        userRepository.save(user);

        List<CategoryAlert> categoryAlerts = categoryAlertService.getCategoryAlertsByUserId(user.getId());

        Assertions.assertEquals(2, categoryAlerts.size());
        Assertions.assertEquals(Category.ALMACEN, categoryAlerts.get(0).getCategory());
        Assertions.assertEquals(Category.BEBIDAS, categoryAlerts.get(1).getCategory());
    }

    @Test
    public void getCategoryAlertFromInexistentUserShouldThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> categoryAlertService.getCategoryAlertsByUserId(0L));
    }

    @Test
    public void removeCategoryAlertById() {
        CategoryAlert categoryAlert1 = CategoryAlertBuilder.aCategoryAlert().withCategory(Category.ALMACEN).withPercentage(10).build();
        CategoryAlert categoryAlert2 = CategoryAlertBuilder.aCategoryAlert().withCategory(Category.BEBIDAS).withPercentage(10).build();
        CategoryAlert catAlertSaved1 = categoryAlertService.save(user.getId(), categoryAlert1);
        categoryAlertService.save(user.getId(), categoryAlert2);

        categoryAlertService.removeById(user.getId(), catAlertSaved1.getId());

        List<CategoryAlert> categoryAlerts = categoryAlertService.getCategoryAlertsByUserId(user.getId());

        Assertions.assertEquals(1, categoryAlerts.size());
        Assertions.assertEquals(Category.BEBIDAS, categoryAlerts.get(0).getCategory());
    }

    @Test
    public void removeCategoryAlertByInexistentIdShouldNoThrowException() {
        Long anyCatAlertId = 0L;
        Assertions.assertDoesNotThrow(() -> categoryAlertService.removeById(user.getId(), anyCatAlertId));
    }

    @Test
    public void removeCategoryFromInexistentUserShouldThrowResourceNotFound() {
        Long anyId = 0L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> categoryAlertService.removeById(anyId, anyId));
    }

    @Test
    public void updateCategory() {
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().withCategory(Category.ALMACEN).withPercentage(10).build();
        CategoryAlert catAlertSaved = categoryAlertService.save(user.getId(), categoryAlert);

        Integer beforePercentage = catAlertSaved.getPercentage();
        catAlertSaved.setPercentage(20);
        categoryAlertService.update(user.getId(), catAlertSaved.getId(), catAlertSaved);

        CategoryAlert catAlert = categoryAlertService.getById(user.getId(), catAlertSaved.getId());

        Assertions.assertEquals(10, beforePercentage);
        Assertions.assertEquals(20, catAlert.getPercentage());
    }

    @Test
    public void updateCategoryWithInexistentUserShouldThrowResourceNotFound() {
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().anyCategoryAlert().build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> categoryAlertService.update(0L, 0L, categoryAlert));
    }
}
