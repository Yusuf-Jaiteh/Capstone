package learn.data;

import learn.model.Appointment;

import java.util.List;

public interface AppointmentRepository {
    Appointment findById(int id);
    List<Appointment> findAll();
    Appointment add(Appointment appointment);
    boolean update(Appointment appointment);
    boolean deleteById(int id);

    List<Appointment> findByCustomerId(int customerId);

    List<Appointment> findByDriverId(int driverId);
}
