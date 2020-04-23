package service.login;

import model.user.Customer;
import service.persistence.daos.CustomerDao;
import service.persistence.exception.DataErrorException;
import service.persistence.exception.UniqueClassException;

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
