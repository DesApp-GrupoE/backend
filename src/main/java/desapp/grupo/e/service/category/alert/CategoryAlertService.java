package desapp.grupo.e.service.category.alert;

import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class CategoryAlertService {

    private UserRepository userRepository;

    public CategoryAlertService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public CategoryAlert save(long idUser, CategoryAlert categoryAlert) {
        User user = findUserById(idUser);
        user.addCategoryAlert(categoryAlert);
        this.userRepository.save(user);
        // obtengo el último guardado ya que de esta manera obtengo el id del categoryAlert
        return user.getCategoryAlerts().get(user.getCategoryAlerts().size() - 1);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", userId)));
    }

    @Transactional(readOnly = true)
    public List<CategoryAlert> getCategoryAlertsByUserId(Long userId) {
        User user = findUserById(userId);
        return user.getCategoryAlerts();
    }

    public void removeById(Long userId, Long id) {
        User user = findUserById(userId);
        user.getCategoryAlerts().stream()
                .filter(ca -> ca.getId().equals(id))
                .findFirst()
                .ifPresent(user::removeCategoryAlert);
        userRepository.save(user);
    }
}
