package learn.models;

public class Reviews {

    private int reviewId;
    private int appointmentId;
    private int customerId;
    private int driverId;
    private int rating;
    private String reviewText;

    public Reviews() {
    }

    public Reviews(int reviewId, int appointmentId, int customerId, int driverId, int rating, String reviewText) {
        this.reviewId = reviewId;
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
