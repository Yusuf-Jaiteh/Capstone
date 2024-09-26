package learn.data;

import learn.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerJdbcTemplateRepositoryTest {

    @Autowired
    CustomerJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        Customer actual = repository.findById(1);
        assertEquals("Muhammed", actual.getFirstName());
    }

    @Test
    void shouldNotFindById(){
        Customer expected = new Customer();
        assertNull(repository.findById(expected.getCustomerId()));

    }

    @Test
    void findAll() {
        List<Customer> customers = repository.findAll();
        assertEquals(3, customers.size());
    }

    @Test
    void add() {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Test");
        customer.setEmail("kdgh@gmailcom");
        customer.setPhoneNumber("123456789");
        customer.setDob(LocalDate.of(1999, 12, 12));
        customer.setGender("female");
        assertNotNull(repository.add(customer));
    }



    @Test
    void update() {
        Customer customer = new Customer();
        customer.setCustomerId(3);
        customer.setFirstName("MyTest");
        customer.setLastName("Mbenga");
        customer.setEmail("mbengam55@gmail.com");
        customer.setPhoneNumber("+220-999-9999");
        customer.setDob(LocalDate.of(1999, 12, 12));
        customer.setGender("female");
        assertTrue(repository.update(customer));
    }

    @Test
    void shouldNotUpdate() {
        Customer customer = new Customer();
        customer.setCustomerId(10);
        customer.setFirstName("Yusuf");
        customer.setLastName("Mbenga");
        customer.setDob(LocalDate.of(2000,2,12));
        assertFalse(repository.update(customer));
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDelete() {
        assertFalse(repository.deleteById(10));
    }
}