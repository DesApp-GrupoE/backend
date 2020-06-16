package desapp.grupo.e.service.commerce;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import desapp.grupo.e.persistence.exception.CommerceDuplicatedException;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;



public class CommerceService {

    private UserRepository userRepository;
    private CommerceRepository commerceRepository;

    public CommerceService(UserRepository userRepository, CommerceRepository commerceRepository) {
        this.userRepository = userRepository;
        this.commerceRepository = commerceRepository;
    }

    @Transactional
    public Commerce save(long idUser, Commerce commerce) {
        if(commerceRepository.existCommerceInDatabase(commerce.getName())) {
            throw new CommerceDuplicatedException(String.format("User already has the commerce '%s'", commerce.getName()));
        }
        User user = findUserById(idUser);
        user.setCommerce(commerce);
        this.userRepository.save(user);
        return user.getCommerce();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", userId)));
    }

    @Transactional
    public void removeById(Long userId, Long id) {
        try {
            //commerceRepository.removeFk(userId, id);
            commerceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            //Si se intenta eliminar un commerce que ya fue eliminado no hacemos nada
        }
    }

    public Commerce getCommerceByUser(Long userId) {
        return commerceRepository.findByUser(userId);
    }
}
