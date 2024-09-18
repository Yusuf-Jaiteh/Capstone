package learn.controllers;

import learn.domain.CustomerService;
import learn.domain.Result;
import learn.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> findById(@PathVariable int customerId) {
        Customer customer = service.findById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Customer customer) {
        Result<Customer> result = service.add(customer);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> update(@PathVariable int customerId, @RequestBody Customer customer) {
        if (customerId != customer.getCustomerId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Customer> result = service.update(customer);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable int customerId, @RequestBody Customer customer) {
        if(customerId != customer.getCustomerId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Customer> result = service.deleteById(customer);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
