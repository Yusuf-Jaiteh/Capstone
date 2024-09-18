package learn.model;

public class Appointment {

    private int appointmentId;
    private int driverId;
    private int customerId;
    private boolean approved;
    private String pickUpLocation;
    private String dropOffLocation;
    //startTime,endTime
    public Appointment() {
    }

    public Appointment(int appointmentId, int driverId, int customerId, boolean approved, String pickUpLocation, String dropOffLocation) {
        this.appointmentId = appointmentId;
        this.driverId = driverId;
        this.customerId = customerId;
        this.approved = approved;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
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

}
