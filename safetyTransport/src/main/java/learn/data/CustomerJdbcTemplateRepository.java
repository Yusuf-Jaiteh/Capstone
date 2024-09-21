package learn.data;

import learn.data.mappers.AppointmentMapper;
import learn.data.mappers.CustomerMapper;
import learn.models.Customer;
import learn.models.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CustomerJdbcTemplateRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Customer findById(int id) {

        final String sql = """
            select * from customers
            where customer_id = ?
            limit 1000;
            """;
        Customer result = jdbcTemplate.query(sql, new CustomerMapper(), id).stream()
                .findFirst()
                .orElse(null);
        
        if (result != null) {
            addAppointments(result);
        }
        return result;
    }

    private void addAppointments(Customer result) {

        final String sql = """
            select * from appointments
            where customer_id = ?;
            """;
        result.setAppointments(jdbcTemplate.query(sql, new AppointmentMapper(), result.getCustomerId()));
    }

    @Override
    @Transactional
    public List<Customer> findAll() {

        final String sql = """
            select * from customers
            """;
        List<Customer> result = jdbcTemplate.query(sql, new CustomerMapper());

        for (Customer customer : result) {
            addAppointments(customer);
        }

        return result;
    }

    @Override
    public Customer add(Customer customer) {

        final String sql = """
            insert into customers (first_name, last_name, email, phone_number)
            values (?,?,?,?);
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        customer.setCustomerId(keyHolder.getKey().intValue());
        return customer;
    }

    @Override
    public boolean update(Customer customer) {

        final String sql = """
            update customers set
            first_name = ?,
            last_name = ?,
            email = ?,
            phone_number = ?
            where customer_id = ?;
            """;
        return jdbcTemplate.update(sql,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getCustomerId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        final String deleteReviewsSql = """
        delete from reviews
        where customer_id = ?;
        """;
        jdbcTemplate.update(deleteReviewsSql, id);

        final String deleteAppointmentsSql = """
        delete from appointments
        where customer_id = ?;
        """;
        jdbcTemplate.update(deleteAppointmentsSql, id);

        final String sql = """
            delete from customers
            where customer_id = ?;
            """;
        return jdbcTemplate.update(sql, id) > 0;
    }
}
