package learn.models;

import java.time.LocalDate;
import java.util.List;

public class Driver {

    private int driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private String carModel;
    private String numberPlate;
    private List<Appointment> appointments;
    private LocalDate dob;
    private String gender;
    private String residentialAddress;
    private String yearsOfExperience;
    private LocalDate licenseExpiryDate;

    public Driver() {
    }

    public Driver(int driverId, String firstName, String lastName, String email, String phoneNumber, String licenseNumber, String carModel, String numberPlate, List<Appointment> appointments,
                  LocalDate dob, String gender, String residentialAddress, String yearsOfExperience, LocalDate licenseExpiryDate) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.carModel = carModel;
        this.numberPlate = numberPlate;
        this.appointments = appointments;
        this.dob = dob;
        this.gender = gender;
        this.residentialAddress = residentialAddress;
        this.yearsOfExperience = yearsOfExperience;
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

}
