package learn.domain;

import learn.data.AppointmentRepository;
import learn.model.Appointment;

import java.util.List;

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

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(appointment));
        return result;
    }

    public Result<Appointment> update(Appointment appointment) {
        Result<Appointment> result = validate(appointment);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(appointment);
        if (!success) {
            result.addErrorMessage("Appointment ID " + appointment.getAppointmentId() + " not found.");
        } else {
            result.setPayload(appointment);
        }

        return result;
    }

    public Result<Appointment> deleteById(int id) {
        Result<Appointment> result = new Result<>();

        if (!repository.deleteById(id)) {
            result.addErrorMessage("Appointment ID " + id + " not found.");
        }

        return result;
    }

    private Result<Appointment> validate(Appointment appointment) {
        Result<Appointment> result = new Result<>();

        if (appointment == null) {
            result.addErrorMessage("Appointment cannot be null.");
            return result;
        }

        if (appointment.getAppointmentId() != 0) {
            result.addErrorMessage("AppointmentId cannot be set for `add` operation.");
        }

        if (appointment.getCustomerId() <= 0) {
            result.addErrorMessage("CustomerId must be set.");
        }

        if (appointment.getAppointmentDate() == null) {
            result.addErrorMessage("AppointmentDate must be set.");
        }

        if (appointment.getStartTime() == null) {
            result.addErrorMessage("StartTime must be set.");
        }

        if (appointment.getEndTime() == null) {
            result.addErrorMessage("EndTime must be set.");
        }

        if (appointment.getStartTime().isAfter(appointment.getEndTime())) {
            result.addErrorMessage("StartTime must be before EndTime.");
        }

        if (appointment.getLocation() == null) {
            result.addErrorMessage("Location must be set.");
        }

        if (appointment.getLocation().getLocationId() <= 0) {
            result.addErrorMessage("LocationId must be set.");
        }

        if (appointment.getDriver() == null) {
            result.addErrorMessage("Driver must be set.");
        }

        if (appointment.getDriver().getDriverId() <= 0) {
            result.addErrorMessage("DriverId must be set.");
        }

        if (appointment.getCost().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Cost must be greater than zero.");
        }

        return result;
    }
}
