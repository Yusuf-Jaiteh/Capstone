package learn.data.mappers;

import learn.models.Driver;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper implements RowMapper<Driver> {

    @Override
    public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {

        Driver driver = new Driver();
        driver.setDriverId(rs.getInt("driver_id"));
        driver.setFirstName(rs.getString("first_name"));
        driver.setLastName(rs.getString("last_name"));
        driver.setPhoneNumber(rs.getString("phone_number"));
        driver.setEmail(rs.getString("email"));
        driver.setLicenseNumber(rs.getString("license_number"));
        driver.setCarModel(rs.getString("car_model"));
        driver.setNumberPlate(rs.getString("number_plate"));

        return driver;
    }
}
