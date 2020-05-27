package desapp.grupo.e.service.category.alert;

import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.category.alert.CategoryAlertRepository;
import desapp.grupo.e.persistence.exception.CategoryDuplicatedException;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class CategoryAlertService {

    private UserRepository userRepository;
    private CategoryAlertRepository categoryAlertRepository;

    public CategoryAlertService(UserRepository userRepository, CategoryAlertRepository categoryAlertRepository) {
        this.userRepository = userRepository;
        this.categoryAlertRepository = categoryAlertRepository;
    }

    @Transactional
    public CategoryAlert save(long idUser, CategoryAlert categoryAlert) {
        if(categoryAlertRepository.existCategoryInUser(idUser, categoryAlert.getCategory().name())) {
            throw new CategoryDuplicatedException(String.format("User already has the category '%s'", categoryAlert.getCategory()));
        }
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

    @Transactional
    public void removeById(Long userId, Long id) {
        try {
            categoryAlertRepository.removeFk(userId, id);
            categoryAlertRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            // Si se intenta eliminar un category alert que ya fue eliminado no hacemos nada
        }
    }

    @Transactional
    public void update(Long userId, Long id, CategoryAlert catAlertUpdated) {
        CategoryAlert categoryAlert = getById(userId, id);
        categoryAlert.setPercentage(catAlertUpdated.getPercentage());
        categoryAlert.setCategory(categoryAlert.getCategory());
        categoryAlertRepository.save(categoryAlert);
    }

    @Transactional(readOnly = true)
    public CategoryAlert getById(Long userId, Long id) {
        return categoryAlertRepository.findByIdAndUser(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("CategoryAlert %s not found", id)));
    }
}
