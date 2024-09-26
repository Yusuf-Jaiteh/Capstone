package learn.data.mappers;

import learn.models.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentMapper implements RowMapper<Appointment> {

    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setDriverId(rs.getInt("driver_id"));
        appointment.setCustomerId(rs.getInt("customer_id"));
        appointment.setApproved(rs.getBoolean("approved"));
        appointment.setPickUpLocation(rs.getString("pickup_location"));
        appointment.setDropOffLocation(rs.getString("dropoff_location"));
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setStartTime(rs.getTime("start_time").toLocalTime());
        appointment.setEndTime(rs.getTime("end_time").toLocalTime());

        return appointment;
    }
}
