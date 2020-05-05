package desapp.grupo.e.persistence.daos;

import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.user.Customer;
import desapp.grupo.e.persistence.exception.DataErrorException;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.AbstractDao;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Service
public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao(EntityManager entityManager) {
        super(entityManager, Customer.class);
    }

    public Boolean existCustomerWithEmail(String email) {
        String hql = "FROM Customer WHERE email = :email";
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        return existsByQuery(hql, params, Customer.class);
    }

    @Override
    public void deleteAll() throws DataErrorException {
        String hql = "delete from " + CategoryAlert.class.getName();
        executeQueryInsideTransaction(hql, new HashMap<>());
        super.deleteAll();
    }
}
