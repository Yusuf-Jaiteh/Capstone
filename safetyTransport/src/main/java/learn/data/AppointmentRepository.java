package learn.data;

import learn.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {
    Appointment findById(int id);
    List<Appointment> findAll();
    Appointment add(Appointment appointment);
    boolean update(Appointment appointment);
    boolean deleteById(int id);

    List<Appointment> findByCustomerId(int customerId);

    List<Appointment> findByDriverId(int driverId);
    List<Appointment> findByDriverIdAndDateAndTime(int driverId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
