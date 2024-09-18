package learn.domain;

import learn.data.AppointmentRepository;
import learn.models.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService service;

    @MockBean
    private AppointmentRepository repository;

    @Test
    void findById() {
        Appointment expected = new Appointment();
        expected.setAppointmentId(1);
        expected.setCustomerId(1);
        expected.setDriverId(1);
        expected.setPickUpLocation("Location A");
        expected.setDropOffLocation("Location B");

        when(repository.findById(expected.getAppointmentId())).thenReturn(expected);

        Appointment actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(expected.getAppointmentId(), actual.getAppointmentId());
        assertEquals(expected.getCustomerId(), actual.getCustomerId());
        assertEquals(expected.getDriverId(), actual.getDriverId());
        assertEquals(expected.getPickUpLocation(), actual.getPickUpLocation());
        assertEquals(expected.getDropOffLocation(), actual.getDropOffLocation());
    }

    @Test
    void findAll() {
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1);
        appointment1.setCustomerId(1);
        appointment1.setDriverId(1);
        appointment1.setPickUpLocation("Location A");
        appointment1.setDropOffLocation("Location B");

        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2);
        appointment2.setCustomerId(2);
        appointment2.setDriverId(2);
        appointment2.setPickUpLocation("Location C");
        appointment2.setDropOffLocation("Location D");

        List<Appointment> expected = Arrays.asList(appointment1, appointment2);

        when(repository.findAll()).thenReturn(expected);

        List<Appointment> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void findByCustomerId() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        List<Appointment> expected = Arrays.asList(appointment);

        when(repository.findByCustomerId(appointment.getCustomerId())).thenReturn(expected);

        List<Appointment> actual = service.findByCustomerId(1);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByDriverId() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        List<Appointment> expected = Arrays.asList(appointment);

        when(repository.findByDriverId(appointment.getDriverId())).thenReturn(expected);

        List<Appointment> actual = service.findByDriverId(1);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void add() {
        Appointment appointment = new Appointment();
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        Appointment expected = new Appointment();
        expected.setAppointmentId(1);
        expected.setCustomerId(1);
        expected.setDriverId(1);
        expected.setPickUpLocation("Location A");
        expected.setDropOffLocation("Location B");

        when(repository.add(appointment)).thenReturn(expected);

        Result<Appointment> result = service.add(appointment);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAdd() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1); // Invalid ID, should be 0 for new appointments
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        Result<Appointment> result = service.add(appointment);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("AppointmentId cannot be set for `add` operation."));
    }

    @Test
    void update() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        when(repository.update(appointment)).thenReturn(true);

        Result<Appointment> result = service.update(appointment);
        assertTrue(result.isSuccess());
        assertEquals(appointment, result.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(0); // Invalid ID, should not be 0 for existing appointments
        appointment.setCustomerId(1);
        appointment.setDriverId(1);
        appointment.setPickUpLocation("Location A");
        appointment.setDropOffLocation("Location B");

        Result<Appointment> result = service.update(appointment);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("AppointmentId must be set."));
    }

    @Test
    void deleteById() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);

        when(repository.deleteById(appointment.getAppointmentId())).thenReturn(true);

        Result<Appointment> result = service.deleteById(appointment);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDelete() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(0); // Invalid ID, should not be 0 for existing appointments

        Result<Appointment> result = service.deleteById(appointment);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Appointment ID 0 not found."));
    }

}
