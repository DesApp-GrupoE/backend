package desapp.grupo.e.persistence.daos;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import desapp.grupo.e.persistence.JPAHibernateTest;
import desapp.grupo.e.persistence.exception.DataErrorException;

import java.util.Optional;

public class UserDAOTest extends JPAHibernateTest {

    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        this.userDao = new UserDao(entityManager);
    }

    @AfterEach
    public void tearDown() throws DataErrorException {
        this.userDao.deleteAll();
    }

    @Test
    public void existUserWithEmail() throws DataErrorException {
        User user = UserBuilder.aUser()
                .anyUser() // Para setear todos los datos
                .withEmail("ariel.ramirez@test.test")
                .build();
        this.userDao.save(user);

        Boolean exists = this.userDao.existUserWithEmail("ariel.ramirez@test.test");
        Assertions.assertTrue(exists);
    }

    @Test
    public void addAlertCategoryToUserAndSaveShouldHave1Alert() throws DataErrorException {
        User user = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        user.addCategoryAlert(categoryAlert);
        this.userDao.save(user);

        Optional<User> optUser = this.userDao.getById(user.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(1, UserFromDb.getCategoryAlerts().size());
    }

    @Test
    public void ifSave2AlertsAndRemove1InUserAndSaveShouldHave1Alert() throws DataErrorException {
        User User = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        User.addCategoryAlert(categoryAlert);
        CategoryAlert categoryAlert2 = new CategoryAlert(Category.BEBIDAS, 15);
        User.addCategoryAlert(categoryAlert2);
        this.userDao.save(User);

        Optional<User> optUser = this.userDao.getById(User.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(2, UserFromDb.getCategoryAlerts().size());

        UserFromDb.removeCategoryAlert(categoryAlert);
        this.userDao.update(UserFromDb);

        Optional<User> optUser2 = this.userDao.getById(User.getId());
        Assertions.assertTrue(optUser2.isPresent());
        User UserFromDb2 = optUser.get();
        Assertions.assertEquals(1, UserFromDb2.getCategoryAlerts().size());
    }

    @Test
    public void ifSave1AlertInUserAndAfterUpdateAlertTheUserShouldHaveThisAlertUpdated() throws DataErrorException {
        User User = UserBuilder.aUser()
                .anyUser()
                .build();

        CategoryAlert categoryAlert = new CategoryAlert(Category.ALMACEN, 10);
        User.addCategoryAlert(categoryAlert);
        this.userDao.save(User);

        categoryAlert.setPercentage(20);
        this.userDao.update(User);

        Optional<User> optUser = this.userDao.getById(User.getId());
        Assertions.assertTrue(optUser.isPresent());
        User UserFromDb = optUser.get();
        Assertions.assertEquals(20, UserFromDb.getCategoryAlerts().get(0).getPercentage());
    }
}
