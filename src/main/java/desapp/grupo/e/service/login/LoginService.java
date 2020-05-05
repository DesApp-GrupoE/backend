package desapp.grupo.e.service.login;

import desapp.grupo.e.model.user.Customer;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.daos.CustomerDao;
import desapp.grupo.e.persistence.exception.DataErrorException;
import desapp.grupo.e.persistence.exception.UniqueClassException;

@Service
public class LoginService {

    private CustomerDao customerDao;

    public LoginService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer signUp(Customer customer) throws DataErrorException, UniqueClassException {
        Boolean exist = this.customerDao.existCustomerWithEmail(customer.getEmail());
        if(exist) {
            throw new UniqueClassException("Email was already registered");
        }
        this.customerDao.save(customer);
        return customer;
    }
}
