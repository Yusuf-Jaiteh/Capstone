package learn.data;

import learn.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DriverJdbcTemplateRepositoryTest {

    @Autowired
    DriverJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findById() {
        Driver driver = new Driver();
        driver.setDriverId(1);
        Driver actual = repository.findById(1);
        assertEquals("Alpha", actual.getFirstName());
    }

    @Test
    void shouldNotFindById() {
        Driver expected = new Driver();
        assertEquals(null, repository.findById(expected.getDriverId()));
    }

    @Test
    void findAll() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void add() {
        Driver driver = new Driver();
        driver.setFirstName("Test");
        driver.setLastName("Test");
        driver.setPhoneNumber("123456789");
        driver.setEmail("testTest@gmail.com");
        driver.setLicenseNumber("GAM 900");
        driver.setNumberPlate("GAM 900");
        driver.setCarModel("Toyota");

        assertEquals(4, repository.add(driver).getDriverId());
    }

    @Test
    void update() {
        Driver driver = new Driver();
        driver.setDriverId(3);
        driver.setFirstName("Test");
        driver.setLastName("Test");
        driver.setPhoneNumber("+220-333-3333");
        driver.setEmail("pgajaga99@gmail.com");
        driver.setLicenseNumber("GAM-9876");
        driver.setNumberPlate("GAM-9876");
        driver.setCarModel("Toyota Corolla");
    }

    @Test
    void shouldNotUpdate() {
        Driver driver = new Driver();
        driver.setDriverId(10000);
        assertFalse(repository.update(driver));
    }
    @Test
    void deleteById() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    @Test
    void shouldNotDelete() {
        assertFalse(repository.deleteById(10000));
    }
}