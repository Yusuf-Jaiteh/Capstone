package learn.domain;

import learn.data.DriverRepository;
import learn.models.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DriverServiceTest {

    @Autowired
    private DriverService service;

    @MockBean
    private DriverRepository repository;

    @Test
    void findById() {
        Driver expected = new Driver();
        expected.setDriverId(1);
        expected.setFirstName("Muhammed");
        expected.setLastName("Dibba");
        expected.setEmail("muhammed10@gmail.com");
        expected.setPhoneNumber("+220-777-7777");

        when(repository.findById(expected.getDriverId())).thenReturn(expected);

        Driver actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(expected.getDriverId(), actual.getDriverId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void findAll() {
        Driver driver1 = new Driver();
        driver1.setDriverId(1);
        driver1.setFirstName("John");
        driver1.setLastName("Doe");

        Driver driver2 = new Driver();
        driver2.setDriverId(2);
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");

        List<Driver> expected = Arrays.asList(driver1, driver2);

        when(repository.findAll()).thenReturn(expected);

        List<Driver> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void add() {
        Driver driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setEmail("john.doe@example.com");
        driver.setPhoneNumber("123456789");
        driver.setLicenseNumber("123456789");
        driver.setCarModel("Toyota");
        driver.setNumberPlate("1234");

        Driver expected = new Driver();
        expected.setDriverId(1);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        expected.setEmail("john.doe@example.com");
        expected.setPhoneNumber("123456789");
        expected.setLicenseNumber("123456789");
        expected.setCarModel("Toyota");
        expected.setNumberPlate("1234");

        when(repository.add(driver)).thenReturn(expected);

        Result<Driver> result = service.add(driver);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAdd() {
        Driver driver = new Driver();
        driver.setDriverId(1); // Invalid ID, should be 0 for new drivers
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setEmail("john.doe@example.com");
        driver.setPhoneNumber("123456789");

        Result<Driver> result = service.add(driver);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("DriverId cannot be set for `add` operation."));
    }

    @Test
    void update() {
        Driver driver = new Driver();
        driver.setDriverId(1);
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setEmail("john.doe@example.com");
        driver.setPhoneNumber("123456789");
        driver.setLicenseNumber("123456789");
        driver.setCarModel("Toyota");
        driver.setNumberPlate("1234");

        when(repository.update(driver)).thenReturn(true);

        Result<Driver> result = service.update(driver);
        assertTrue(result.isSuccess());
        assertEquals(driver, result.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Driver driver = new Driver();
        driver.setDriverId(0); // Invalid ID, should not be 0 for existing drivers
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setEmail("john.doe@example.com");
        driver.setPhoneNumber("123456789");
        driver.setLicenseNumber("123456789");
        driver.setCarModel("Toyota");
        driver.setNumberPlate("1234");

        Result<Driver> result = service.update(driver);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Driver ID must be set."));
    }

    @Test
    void deleteById() {
        Driver driver = new Driver();
        driver.setDriverId(1);

        when(repository.deleteById(driver.getDriverId())).thenReturn(true);

        Result<Driver> result = service.deleteById(driver);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDelete() {
        Driver driver = new Driver();
        driver.setDriverId(0); // Invalid ID, should not be 0 for existing drivers

        Result<Driver> result = service.deleteById(driver);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Driver ID 0 not found."));
    }
}