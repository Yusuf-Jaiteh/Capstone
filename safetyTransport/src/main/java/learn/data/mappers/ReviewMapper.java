package learn.data.mappers;

import learn.model.Reviews;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Reviews> {

    @Override
    public Reviews mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Reviews review = new Reviews();

        review.setReviewId(resultSet.getInt("review_id"));
        review.setDriverId(resultSet.getInt("driver_id"));
        review.setRating(resultSet.getInt("rating"));
        review.setReviewText(resultSet.getString("review_text"));
        review.setAppointmentId(resultSet.getInt("appointment_id"));
        review.setCustomerId(resultSet.getInt("customer_id"));

        return review;
    }

}
