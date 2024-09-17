package learn.data;

import learn.data.mappers.ReviewMapper;
import learn.model.Reviews;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReviewJdbcTemplateRepository implements ReviewsRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reviews findById(int id) {

        final String sql = """
            select * from reviews
            where review_id = ?
            limit 1000;
            """;

        return jdbcTemplate.query(sql, new ReviewMapper(), id).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reviews> findAll() {

        final String sql = """
            select * from reviews
            """;

        return jdbcTemplate.query(sql, new ReviewMapper());
    }

    @Override
    public Reviews add(Reviews review) {

        final String sql = """
            insert into reviews (appointment_id, customer_id, driver_id, rating, review_text)
            values (?,?,?,?,?);
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getAppointmentId());
            ps.setInt(2, review.getCustomerId());
            ps.setInt(3, review.getDriverId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getReviewText());
            return ps;
        }, keyHolder);

        if(rowAffected <= 0) {
            return null;
        }
        review.setReviewId(keyHolder.getKey().intValue());
        return review;
    }

    @Override
    public boolean update(Reviews review) {

        final String sql = """
            update reviews set
            appointment_id = ?,
            customer_id = ?,
            driver_id = ?,
            rating = ?,
            review_text = ?
            where review_id = ?;
            """;
        return jdbcTemplate.update(sql,
                review.getAppointmentId(),
                review.getCustomerId(),
                review.getDriverId(),
                review.getRating(),
                review.getReviewText(),
                review.getReviewId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {

        final String sql = """
            delete from reviews
            where review_id = ?;
            """;
        return jdbcTemplate.update(sql, id) > 0;
    }
}
