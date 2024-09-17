package learn.domain;

import learn.data.CustomerRepository;
import learn.model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer findById(int id) {
        return repository.findById(id);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Result<Customer> add(Customer customer) {
        Result<Customer> result = validate(customer);

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(customer));
        return result;
    }

   public Result<Customer> update(Customer customer) {
        Result<Customer> result = validate(customer);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(customer);
        if (!success) {
            result.addErrorMessage("Customer ID " + customer.getCustomerId() + " not found.");
        } else {
            result.setPayload(customer);
        }

        return result;
    }

    public Result<Customer> deleteById(Customer customer) {
        Result<Customer> result = new Result<>();
        if (customer == null) {
            result.addErrorMessage("Customer cannot be null.");
            return result;
        }

        if (!repository.deleteById(customer.getCustomerId())) {
            result.addErrorMessage("Customer ID " + customer.getCustomerId() + " not found.");
        }
        return result;
    }

    private Result<Customer> validate(Customer customer) {
        Result<Customer> result = new Result<>();

        if (customer == null) {
            result.addErrorMessage("Customer cannot be null.");
            return result;
        }

        if (customer.getCustomerId() != 0) {
            result.addErrorMessage("Customer ID must be 0.");
        }

        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            result.addErrorMessage("First name is required.");
        }

        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            result.addErrorMessage("Last name is required.");
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            result.addErrorMessage("Email is required.");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            result.addErrorMessage("Phone is required.");
        }

        return result;
    }
}
