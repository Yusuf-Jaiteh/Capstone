package learn.data;

import learn.model.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer findById(int id);
    List<Customer> findAll();
    Customer add(Customer customer);
    boolean update(Customer customer);
    boolean deleteById(int id);
}
