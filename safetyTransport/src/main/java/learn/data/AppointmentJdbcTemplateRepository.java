package learn.data;

import learn.model.Appointment;
import learn.data.mappers.AppointmentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AppointmentJdbcTemplateRepository implements AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Appointment findById(int id) {

        final String sql = """
            select * from appointments
            where appointment_id = ?
            limit 1000;
            """;
        return jdbcTemplate.query(sql, new AppointmentMapper(), id).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Appointment> findAll() {

        final String sql = """
            select * from appointments
            """;
        return jdbcTemplate.query(sql, new AppointmentMapper());
    }

    @Override
    public List<Appointment> findByCustomerId(int customerId) {
        final String sql = """
                select * from appointments
                where customer_id = ?
                """;
        return jdbcTemplate.query(sql, new AppointmentMapper(), customerId);
    }

    @Override
    public List<Appointment> findByDriverId(int driverId) {

        final String sql = """
            select * from appointments
            where driver_id = ?
            """;
        return jdbcTemplate.query(sql, new AppointmentMapper(), driverId);
    }

    @Override
    public Appointment add(Appointment appointment) {

        final String sql = """
            insert into appointments (customer_id, driver_id, approved, pickup_location, dropoff_location)
            values (?,?,?,?,?);
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, appointment.getCustomerId());
            ps.setInt(2, appointment.getDriverId());
            ps.setBoolean(3, appointment.isApproved());
            ps.setString(4, appointment.getPickUpLocation());
            ps.setString(5, appointment.getDropOffLocation());
            return ps;
        }, keyHolder);

        if(rowAffected <= 0) {
            return null;
        }
        appointment.setAppointmentId(keyHolder.getKey().intValue());
        return appointment;
    }

    @Override
    public boolean update(Appointment appointment) {

        final String sql = """
            update appointments set
            customer_id = ?,
            driver_id = ?,
            approved = ?,
            pickup_location = ?,
            dropoff_location = ?
            where appointment_id = ?;
            """;

        return jdbcTemplate.update(sql,
                appointment.getCustomerId(),
                appointment.getDriverId(),
                appointment.isApproved(),
                appointment.getPickUpLocation(),
                appointment.getDropOffLocation(),
                appointment.getAppointmentId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {

        final String deleteReviewsSql = """
        delete from reviews
        where customer_id = ?;
        """;
        jdbcTemplate.update(deleteReviewsSql, id);

        final String sql = """
            delete from appointments
            where appointment_id = ?;
            """;

        return jdbcTemplate.update(sql, id) > 0;
    }

}
