package learn.domain;

import learn.data.CustomerRepository;
import learn.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        if (customer.getCustomerId() != 0) {
            result.addMessage("Customer ID must be 0.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(customer));
        return result;
    }

   public Result<Customer> update(Customer customer) {
        Result<Customer> result = validate(customer);

        if (customer.getCustomerId() <= 0) {
            result.addMessage("Customer ID must be set.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(customer);
        if (!success) {
            result.addMessage("Customer ID " + customer.getCustomerId() + " not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(customer);
        }

        return result;
    }

    public Result<Customer> deleteById(Customer customer) {
        Result<Customer> result = new Result<>();
        if (customer == null) {
            result.addMessage("Customer cannot be null.", ResultType.INVALID);
            return result;
        }

        if (customer.getCustomerId() <= 0) {
            result.addMessage("Customer ID must be set.", ResultType.INVALID);
        }

        if (!repository.deleteById(customer.getCustomerId())) {
            result.addMessage("Customer ID " + customer.getCustomerId() + " not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Customer> validate(Customer customer) {
        Result<Customer> result = new Result<>();

        if (customer == null) {
            result.addMessage("Customer cannot be null.", ResultType.INVALID);
            return result;
        }


        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            result.addMessage("First name is required.", ResultType.INVALID);
        }

        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            result.addMessage("Last name is required.", ResultType.INVALID);
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            result.addMessage("Email is required.", ResultType.INVALID);
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            result.addMessage("Phone Number is required.", ResultType.INVALID);
        }

        if (customer.getDob() == null) {
            result.addMessage("Date of Birth is required.", ResultType.INVALID);
        }

        if (customer.getDob() != null && customer.getDob().isAfter(java.time.LocalDate.now())) {
            result.addMessage("Date of Birth cannot be in the future.", ResultType.INVALID);
        }

        if (customer.getGender() == null || customer.getGender().isBlank()) {
            result.addMessage("Gender is required.", ResultType.INVALID);
        }

        return result;
    }
}
