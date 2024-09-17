package learn.data;

import learn.data.mappers.AppointmentMapper;
import learn.data.mappers.DriverMapper;
import learn.model.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DriverJdbcTemplateRepository implements DriverRepository {

    private final JdbcTemplate jdbcTemplate;

    public DriverJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Driver findById(int id) {

        final String sql = """
            select * from drivers
            where driver_id = ?
            limit 1000;
            """;
        Driver result = jdbcTemplate.query(sql, new DriverMapper(), id).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
             addAppointments(result);
        }
        return result;
    }

    private void addAppointments(Driver result) {

        final String sql = """
            select * from appointments
            where driver_id = ?;
            """;
        result.setAppointments(jdbcTemplate.query(sql, new AppointmentMapper(), result.getDriverId()));
    }

    @Override
    public List<Driver> findAll() {

        final String sql = """
            select * from drivers
            """;

        return jdbcTemplate.query(sql, new DriverMapper());
    }

    @Override
    public Driver add(Driver driver) {

        final String sql = """
            insert into drivers (first_name, last_name, email, phone_number, license_number, car_model, number_plate)
            values (?,?,?,?,?,?,?);
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, driver.getFirstName());
            ps.setString(2, driver.getLastName());
            ps.setString(3, driver.getEmail());
            ps.setString(4, driver.getPhoneNumber());
            ps.setString(5, driver.getLicenseNumber());
            ps.setString(6, driver.getCarModel());
            ps.setString(7, driver.getNumberPlate());
            return ps;
        }, keyHolder);

        if(rowAffected <= 0) {
            return null;
        }

        driver.setDriverId(keyHolder.getKey().intValue());
        return driver;
    }

    @Override
    public boolean update(Driver driver) {

        final String sql = """
            update drivers set
            first_name = ?,
            last_name = ?,
            email = ?,
            phone_number = ?,
            license_number = ?,
            car_model = ?,
            number_plate = ?
            where driver_id = ?;
            """;
        return jdbcTemplate.update(sql,
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getPhoneNumber(),
                driver.getLicenseNumber(),
                driver.getCarModel(),
                driver.getNumberPlate(),
                driver.getDriverId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        final String deleteReviewsSql = """
        delete from reviews
        where driver_id = ?;
        """;
        jdbcTemplate.update(deleteReviewsSql, id);

        final String deleteAppointmentsSql = """
        delete from appointments
        where driver_id = ?;
        """;
        jdbcTemplate.update(deleteAppointmentsSql, id);

        final String sql = """
            delete from drivers
            where driver_id = ?;
            """;
        return jdbcTemplate.update(sql, id) > 0;
    }
}
