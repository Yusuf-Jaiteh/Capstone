package learn.data;

import learn.models.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppointmentJdbcTemplateRepositoryTest {

    @Autowired
    AppointmentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findById() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        Appointment actual = repository.findById(1);
        assertEquals("Bakau", actual.getPickUpLocation());
    }

    @Test
    void shouldNotFindById() {
        Appointment expected = new Appointment();
        assertEquals(null, repository.findById(expected.getAppointmentId()));
    }

    @Test
    void findAll() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void shouldFindByCustomerId() {
        assertEquals(1, repository.findByCustomerId(1).size());
    }

    @Test
    void shouldFindByDriverId() {
        assertEquals(1, repository.findByDriverId(3).size());
    }

    @Test
    void add() {
        Appointment appointment = new Appointment();
        appointment.setPickUpLocation("Banjul");
        appointment.setDropOffLocation("Brikama");
        appointment.setApproved(false);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setAppointmentDate(LocalDate.of(2022,01,01));
        appointment.setStartTime(LocalTime.of(12,00,00));
        appointment.setEndTime(LocalTime.of(13,00,00));

        assertEquals(4, repository.add(appointment).getAppointmentId());
    }

    @Test
    void update() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(2);
        appointment.setPickUpLocation("Serrekunda");
        appointment.setDropOffLocation("Bakau");
        appointment.setAppointmentDate(LocalDate.of(2022,01,01));
        appointment.setStartTime(LocalTime.of(12,00,00));
        appointment.setEndTime(LocalTime.of(13,00,00));
        appointment.setApproved(true);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);

        assertTrue(repository.update(appointment));
    }

    @Test
    void shouldNotUpdate() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1000);
        appointment.setPickUpLocation("Serrekunda");
        appointment.setDropOffLocation("Bakau");
        appointment.setAppointmentDate(LocalDate.of(2022,01,01));
        appointment.setStartTime(LocalTime.of(12,00,00));
        appointment.setEndTime(LocalTime.of(13,00,00));
        appointment.setApproved(true);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);

        assertFalse(repository.update(appointment));
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    @Test
    void shouldNotDelete() {
        assertFalse(repository.deleteById(1000));
    }

}