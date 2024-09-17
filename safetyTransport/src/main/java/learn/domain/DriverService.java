package learn.domain;

import learn.data.DriverRepository;
import learn.model.Driver;

import java.util.List;

public class DriverService {
    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Driver findById(int id) {
        return repository.findById(id);
    }

    public List<Driver> findAll() {
        return repository.findAll();
    }

    public Result<Driver> add(Driver driver) {
        Result<Driver> result = validate(driver);

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(driver));
        return result;
    }

    public Result<Driver> update(Driver driver) {
        Result<Driver> result = validate(driver);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(driver);
        if (!success) {
            result.addErrorMessage("Driver ID " + driver.getDriverId() + " not found.");
        } else {
            result.setPayload(driver);
        }

        return result;
    }

    public Result<Driver> deleteById(Driver driver) {
        Result<Driver> result = new Result<>();
        if (driver == null) {
            result.addErrorMessage("Driver cannot be null.");
            return result;
        }

        if (!repository.deleteById(driver.getDriverId())) {
            result.addErrorMessage("Driver ID " + driver.getDriverId() + " not found.");
        }
        return result;
    }

    private Result<Driver> validate(Driver driver) {
        Result<Driver> result = new Result<>();

        if (driver == null) {
            result.addErrorMessage("Driver cannot be null.");
            return result;
        }

        if (driver.getDriverId() != 0) {
            result.addErrorMessage("DriverId cannot be set for `add` operation.");
        }

        if (driver.getFirstName() == null || driver.getFirstName().isBlank()) {
            result.addErrorMessage("First Name is required.");
        }

        if (driver.getLastName() == null || driver.getLastName().isBlank()) {
            result.addErrorMessage("Last Name is required.");
        }

        if (driver.getLicenseNumber() == null || driver.getLicenseNumber().isBlank()) {
            result.addErrorMessage("License Number is required.");
        }

        if (driver.getNumberPlate() == null || driver.getNumberPlate().isBlank()) {
            result.addErrorMessage("Number Plate is required.");
        }

        if (driver.getEmail() == null || driver.getEmail().isBlank()) {
            result.addErrorMessage("Email is required.");
        }

        return result;
    }

}
