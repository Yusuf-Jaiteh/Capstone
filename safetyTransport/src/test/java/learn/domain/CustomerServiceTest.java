package learn.domain;

import learn.data.CustomerRepository;
import learn.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerServiceTest {


    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;

    @Test
    void findById() {
        Customer expected = new Customer();
        expected.setCustomerId(1);
        expected.setFirstName("Muhammed");
        expected.setLastName("Dibba");
        expected.setEmail("muhammed10@gmail.com");
        expected.setPhoneNumber("+220-777-7777");

        when(repository.findById(expected.getCustomerId())).thenReturn(expected);

        Customer actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(expected.getCustomerId(), actual.getCustomerId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void findAll() {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2);
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");


        List<Customer> expected = Arrays.asList(customer1, customer2);

        when(repository.findAll()).thenReturn(expected);

        List<Customer> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }


    @Test
    void add() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("123456789");

        Customer expected = new Customer();
        expected.setCustomerId(1);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        expected.setEmail("john.doe@example.com");
        expected.setPhoneNumber("123456789");

        when(repository.add(customer)).thenReturn(expected);

        Result<Customer> result = service.add(customer);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAdd() {
        Customer customer = new Customer();
        customer.setCustomerId(1); // Invalid ID, should be 0 for new customers
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("123456789");

        Result<Customer> result = service.add(customer);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Customer ID must be 0."));
    }

    @Test
    void update() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("123456789");

        when(repository.update(customer)).thenReturn(true);

        Result<Customer> result = service.update(customer);
        assertTrue(result.isSuccess());
        assertEquals(customer, result.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Customer customer = new Customer();
        customer.setCustomerId(0); // Invalid ID, should not be 0 for existing customers
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("123456789");

        Result<Customer> result = service.update(customer);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Customer ID must be set."));
    }

    @Test
    void deleteById() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        when(repository.deleteById(customer.getCustomerId())).thenReturn(true);

        Result<Customer> result = service.deleteById(customer);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDelete() {
        Customer customer = new Customer();
        customer.setCustomerId(0); // Invalid ID, should not be 0 for existing customers

        Result<Customer> result = service.deleteById(customer);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Customer ID 0 not found."));
    }
}