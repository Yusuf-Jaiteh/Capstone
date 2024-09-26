package learn.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private int appointmentId;
    private int driverId;
    private int customerId;
    private boolean approved;
    private String pickUpLocation;
    private String dropOffLocation;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Appointment() {
    }

    public Appointment(int appointmentId, int driverId, int customerId, boolean approved, String pickUpLocation, String dropOffLocation,
                       LocalDate appointmentDate, LocalTime startTime, LocalTime endTime) {
        this.appointmentId = appointmentId;
        this.driverId = driverId;
        this.customerId = customerId;
        this.approved = approved;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
