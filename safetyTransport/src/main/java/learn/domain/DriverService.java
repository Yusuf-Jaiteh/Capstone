package learn.domain;

import learn.data.DriverRepository;
import learn.model.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        if (driver.getDriverId() != 0) {
            result.addMessage("DriverId cannot be set for `add` operation.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(driver));
        return result;
    }

    public Result<Driver> update(Driver driver) {
        Result<Driver> result = validate(driver);

        if (driver.getDriverId() <= 0) {
            result.addMessage("Driver ID must be set.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(driver);
        if (!success) {
            result.addMessage("Driver ID " + driver.getDriverId() + " not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(driver);
        }

        return result;
    }

    public Result<Driver> deleteById(Driver driver) {
        Result<Driver> result = new Result<>();
        if (driver == null) {
            result.addMessage("Driver cannot be null.", ResultType.INVALID);
            return result;
        }

        if (driver.getDriverId() <= 0) {
            result.addMessage("Driver ID must be set.", ResultType.INVALID);
        }

        if (!repository.deleteById(driver.getDriverId())) {
            result.addMessage("Driver ID " + driver.getDriverId() + " not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Driver> validate(Driver driver) {
        Result<Driver> result = new Result<>();

        if (driver == null) {
            result.addMessage("Driver cannot be null.", ResultType.INVALID);
            return result;
        }


        if (driver.getFirstName() == null || driver.getFirstName().isBlank()) {
            result.addMessage("First Name is required.", ResultType.INVALID);
        }

        if (driver.getLastName() == null || driver.getLastName().isBlank()) {
            result.addMessage("Last Name is required.", ResultType.INVALID);
        }

        if (driver.getLicenseNumber() == null || driver.getLicenseNumber().isBlank()) {
            result.addMessage("License Number is required.", ResultType.INVALID);
        }

        if (driver.getNumberPlate() == null || driver.getNumberPlate().isBlank()) {
            result.addMessage("Number Plate is required.", ResultType.INVALID);
        }

        if (driver.getEmail() == null || driver.getEmail().isBlank()) {
            result.addMessage("Email is required.", ResultType.INVALID);
        }

        return result;
    }

}
