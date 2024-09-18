package learn.data;

import learn.model.Reviews;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewJdbcTemplateRepositoryTest {

    @Autowired
    ReviewJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findById() {
        Reviews actual = repository.findById(1);
        assertEquals("Great service", actual.getReviewText());
    }

    @Test
    void shouldNotFindById() {
        assertNull(repository.findById(1000));
    }

    @Test
    void findAll() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void shouldFindByCustomerId() {
        assertEquals(1, repository.findByCustomerId(1).size());
    }

    @Test
    void shouldFindByDriverId() {
        assertEquals(1, repository.findByDriverId(3).size());
    }

    @Test
    void add() {
        Reviews reviews = new Reviews();
        reviews.setReviewText("Test");
        reviews.setCustomerId(1);
        reviews.setDriverId(1);
        reviews.setRating(5);
        reviews.setAppointmentId(3);
    }

    @Test
    void update() {
        Reviews reviews = new Reviews();
        reviews.setReviewId(2);
        reviews.setReviewText("Test");
        reviews.setCustomerId(1);
        reviews.setDriverId(1);
        reviews.setRating(5);
        reviews.setAppointmentId(3);
        assertTrue(repository.update(reviews));
    }

    @Test
    void shouldNotUpdate() {
        Reviews reviews = new Reviews();
        reviews.setReviewId(1000);
        reviews.setReviewText("Test");
        reviews.setCustomerId(1);
        reviews.setDriverId(1);
        reviews.setRating(5);
        reviews.setAppointmentId(3);
        assertFalse(repository.update(reviews));
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDelete() {
        assertFalse(repository.deleteById(1000));
    }
}