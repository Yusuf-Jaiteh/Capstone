package learn.controllers;

import learn.domain.DriverService;
import learn.domain.Result;
import learn.models.Driver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> findById(@PathVariable int driverId) {
        Driver driver = service.findById(driverId);
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Driver driver) {
        Result<Driver> result = service.add(driver);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<Object> update(@PathVariable int driverId, @RequestBody Driver driver) {
        if (driverId != driver.getDriverId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Driver> result = service.update(driver);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteById(@PathVariable int driverId, @RequestBody Driver driver) {
        if (driverId != driver.getDriverId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Driver> result = service.deleteById(driver);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
