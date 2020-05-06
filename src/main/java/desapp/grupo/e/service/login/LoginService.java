package desapp.grupo.e.service.login;

import desapp.grupo.e.model.user.User;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.daos.UserDao;
import desapp.grupo.e.persistence.exception.DataErrorException;
import desapp.grupo.e.persistence.exception.UniqueClassException;

@Service
public class LoginService {

    private UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User signUp(User customer) throws DataErrorException, UniqueClassException {
        Boolean exist = this.userDao.existUserWithEmail(customer.getEmail());
        if(exist) {
            throw new UniqueClassException("Email was already registered");
        }
        this.userDao.save(customer);
        return customer;
    }
}
