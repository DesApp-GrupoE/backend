package desapp.grupo.e.persistence.daos;

import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.exception.DataErrorException;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.AbstractDao;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Service
public class UserDao extends AbstractDao<User> {

    public UserDao(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    public Boolean existUserWithEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        return existsByQuery(hql, params, User.class);
    }

    @Override
    public void deleteAll() throws DataErrorException {
        String hql = "delete from " + CategoryAlert.class.getName();
        executeQueryInsideTransaction(hql, new HashMap<>());
        super.deleteAll();
    }
}
