package learn.data;

import learn.data.mappers.AppointmentMapper;
import learn.data.mappers.DriverMapper;
import learn.models.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

        List<Driver> result = jdbcTemplate.query(sql, new DriverMapper());

        for (Driver driver : result) {
            addAppointments(driver);
        }

        return result;
    }

    @Override
    public Driver add(Driver driver) {

        if (emailExists(driver.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + driver.getEmail());
        }

        final String sql = """
            insert into drivers (first_name, last_name, email, phone_number, license_number, car_model, number_plate, 
                
                dob , gender, residential_address, years_of_experience, license_expiry_date)
            values (?,?,?,?,?,?,?,?,?,?,?,?);
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
            ps.setDate(8, Date.valueOf(driver.getDob()));
            ps.setString(9, driver.getGender());
            ps.setString(10, driver.getResidentialAddress());
            ps.setString(11, driver.getYearsOfExperience());
            ps.setDate(12, Date.valueOf(driver.getLicenseExpiryDate()));
            return ps;
        }, keyHolder);

        if(rowAffected <= 0) {
            return null;
        }

        driver.setDriverId(keyHolder.getKey().intValue());
        return driver;
    }

    private boolean emailExists(String email) {
        final String sql = "select count(*) from drivers where email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
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
