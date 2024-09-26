package learn.domain;

import learn.data.AppointmentRepository;
import learn.models.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment findById(int id) {
        return repository.findById(id);
    }

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public List<Appointment> findByCustomerId(int customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Appointment> findByDriverId(int driverId) {
        return repository.findByDriverId(driverId);
    }

    public Result<Appointment> add(Appointment appointment) {
        Result<Appointment> result = validate(appointment);

        if (appointment.getAppointmentId() != 0) {
            result.addMessage("AppointmentId cannot be set for `add` operation.", ResultType.INVALID);
        }

        if (!repository.findByDriverIdAndDateAndTime(appointment.getDriverId(), appointment.getAppointmentDate(), appointment.getStartTime(), appointment.getEndTime()).isEmpty()) {
            result.addMessage("Driver already has an appointment at the same date and time.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(appointment));
        return result;
    }

    public Result<Appointment> update(Appointment appointment) {
        Result<Appointment> result = validate(appointment);

        if (appointment.getAppointmentId() <= 0) {
            result.addMessage("AppointmentId must be set.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(appointment);
        if (!success) {
            result.addMessage("Appointment ID " + appointment.getAppointmentId() + " not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(appointment);
        }

        return result;
    }

    public Result<Appointment> deleteById(Appointment appointment) {
        Result<Appointment> result = new Result<>();

        if (appointment == null) {
            result.addMessage("Appointment cannot be null.", ResultType.INVALID);
            return result;
        }

        if (appointment.getAppointmentId() <= 0) {
            result.addMessage("AppointmentId must be set", ResultType.INVALID);
        }

        if (!repository.deleteById(appointment.getAppointmentId())) {
            result.addMessage("Appointment ID " + appointment.getAppointmentId() + " not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Appointment> validate(Appointment appointment) {
        Result<Appointment> result = new Result<>();

        if (appointment == null) {
            result.addMessage("Appointment cannot be null.", ResultType.INVALID);
            return result;
        }

        if (appointment.getCustomerId() <= 0) {
            result.addMessage("CustomerId must be set.", ResultType.INVALID);
        }

        if (appointment.getPickUpLocation() == null || appointment.getPickUpLocation().isEmpty()) {
            result.addMessage("Pick Up Location must be set.", ResultType.INVALID);
        }

        if (appointment.getDropOffLocation() == null || appointment.getDropOffLocation().isEmpty()) {
            result.addMessage("Drop off Location must be set.", ResultType.INVALID);
        }

        if (appointment.getDriverId() <= 0) {
            result.addMessage("DriverId must be set.", ResultType.INVALID);
        }

        if(appointment.getStartTime().isAfter(appointment.getEndTime())) {
            result.addMessage("Start time must be before end time.", ResultType.INVALID);
        }

        if(appointment.getAppointmentDate().isBefore(java.time.LocalDate.now())) {
            result.addMessage("Appointment date must be in the future.", ResultType.INVALID);
        }



        return result;
    }
}
