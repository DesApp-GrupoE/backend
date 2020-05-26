package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.builder.product.CategoryAlertBuilder;
import desapp.grupo.e.model.product.CategoryAlert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UserTest {

    @Test
    public void whenANewUserGetCategoryAlertsThenShouldGetAnEmptyList() {
        User user = UserBuilder.aUser().anyUser().build();

        Assertions.assertTrue(user.getCategoryAlerts().isEmpty());
    }

    @Test
    public void whenAUserAddACategoryAlertThenCategoryAlertsIsNotEmpty() {
        User user = UserBuilder.aUser().anyUser().build();
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().anyCategoryAlert().build();

        user.addCategoryAlert(categoryAlert);

        Assertions.assertFalse(user.getCategoryAlerts().isEmpty());
    }

    @Test
    public void whenAUserHasA1CategoryAlertAndRemoveItThenWhenGetCategoryAlertsThisListsIsEmpty() {
        User user = UserBuilder.aUser().anyUser().build();
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().anyCategoryAlert().build();
        user.addCategoryAlert(categoryAlert);

        user.removeCategoryAlert(categoryAlert);

        Assertions.assertTrue(user.getCategoryAlerts().isEmpty());
    }
}
