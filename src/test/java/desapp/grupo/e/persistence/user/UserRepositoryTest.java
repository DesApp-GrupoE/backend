package desapp.grupo.e.persistence.user;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.webservice.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    public void existUserWithEmail() {
        User user = UserBuilder.aUser()
                .anyUser() // Para setear todos los datos
                .withEmail("ariel.ramirez@test.test")
                .build();
        this.userRepository.save(user);

        Optional<User> optUser = this.userRepository.findByEmail("ariel.ramirez@test.test");
        Assertions.assertTrue(optUser.isPresent());
    }

    @Test
    public void addAlertCategoryToUserAndSaveShouldHave1Alert() {
        User user = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        user.addCategoryAlert(categoryAlert);
        this.userRepository.save(user);

        Optional<User> optUser = this.userRepository.findById(user.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(1, UserFromDb.getCategoryAlerts().size());
    }

    @Test
    public void ifSave2AlertsAndRemove1InUserAndSaveShouldHave1Alert() {
        User User = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        User.addCategoryAlert(categoryAlert);
        CategoryAlert categoryAlert2 = new CategoryAlert(Category.BEBIDAS, 15);
        User.addCategoryAlert(categoryAlert2);
        this.userRepository.save(User);

        Optional<User> optUser = this.userRepository.findById(User.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(2, UserFromDb.getCategoryAlerts().size());

        UserFromDb.removeCategoryAlert(categoryAlert);
        this.userRepository.save(UserFromDb);

        Optional<User> optUser2 = this.userRepository.findById(User.getId());
        Assertions.assertTrue(optUser2.isPresent());
        User UserFromDb2 = optUser.get();
        Assertions.assertEquals(1, UserFromDb2.getCategoryAlerts().size());
    }

    @Test
    public void ifSave1AlertInUserAndAfterUpdateAlertTheUserShouldHaveThisAlertUpdated() {
        User User = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        User.addCategoryAlert(categoryAlert);
        this.userRepository.save(User);

        categoryAlert.setPercentage(20);
        this.userRepository.save(User);

        Optional<User> optUser = this.userRepository.findById(User.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(20, UserFromDb.getCategoryAlerts().get(0).getPercentage());
    }
}
