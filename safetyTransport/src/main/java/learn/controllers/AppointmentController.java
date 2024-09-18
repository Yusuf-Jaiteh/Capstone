package learn.controllers;

import learn.domain.AppointmentService;
import learn.domain.Result;
import learn.models.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> findById(@PathVariable int appointmentId) {
        Appointment appointment = service.findById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> findByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(service.findByCustomerId(customerId));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Object> findByAgentId(@PathVariable int driverId) {
        return ResponseEntity.ok(service.findByDriverId(driverId));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Appointment appointment) {
        Result<Appointment> result = service.add(appointment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Object> update(@PathVariable int appointmentId, @RequestBody Appointment appointment) {
        if (appointmentId != appointment.getAppointmentId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Appointment> result = service.update(appointment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteById(@PathVariable int appointmentId, @RequestBody Appointment appointment) {
        if(appointmentId != appointment.getAppointmentId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Appointment> result = service.deleteById(appointment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
