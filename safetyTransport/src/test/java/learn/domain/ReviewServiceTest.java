package learn.domain;

import learn.data.ReviewsRepository;
import learn.models.Reviews;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @MockBean
    ReviewsRepository reviewsRepository;

    @Test
    void findById() {
        Reviews expected = new Reviews();
        expected.setReviewId(1);
        expected.setAppointmentId(1);
        expected.setCustomerId(1);
        expected.setDriverId(1);
        expected.setReviewText("Great service!");
        expected.setRating(5);

        when(reviewsRepository.findById(expected.getReviewId())).thenReturn(expected);

        Reviews actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(expected.getReviewId(), actual.getReviewId());
        assertEquals(expected.getAppointmentId(), actual.getAppointmentId());
        assertEquals(expected.getCustomerId(), actual.getCustomerId());
        assertEquals(expected.getDriverId(), actual.getDriverId());
        assertEquals(expected.getReviewText(), actual.getReviewText());
        assertEquals(expected.getRating(), actual.getRating());
    }

    @Test
    void findAll() {
        Reviews review1 = new Reviews();
        review1.setReviewId(1);
        review1.setAppointmentId(1);
        review1.setCustomerId(1);
        review1.setDriverId(1);
        review1.setReviewText("Great service!");
        review1.setRating(5);

        Reviews review2 = new Reviews();
        review2.setReviewId(2);
        review2.setAppointmentId(2);
        review2.setCustomerId(2);
        review2.setDriverId(2);
        review2.setReviewText("Good service!");
        review2.setRating(4);

        List<Reviews> expected = Arrays.asList(review1, review2);

        when(reviewsRepository.findAll()).thenReturn(expected);

        List<Reviews> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void findByCustomerId() {
        Reviews review = new Reviews();
        review.setReviewId(1);
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        List<Reviews> expected = Arrays.asList(review);

        when(reviewsRepository.findByCustomerId(review.getCustomerId())).thenReturn(expected);

        List<Reviews> actual = service.findByCustomerId(1);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByDriverId() {
        Reviews review = new Reviews();
        review.setReviewId(1);
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        List<Reviews> expected = Arrays.asList(review);

        when(reviewsRepository.findByDriverId(review.getDriverId())).thenReturn(expected);

        List<Reviews> actual = service.findByDriverId(1);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void add() {
        Reviews review = new Reviews();
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        Reviews expected = new Reviews();
        expected.setReviewId(1);
        expected.setAppointmentId(1);
        expected.setCustomerId(1);
        expected.setDriverId(1);
        expected.setReviewText("Great service!");
        expected.setRating(5);

        when(reviewsRepository.add(review)).thenReturn(expected);

        Result<Reviews> result = service.add(review);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAdd() {
        Reviews review = new Reviews();
        review.setReviewId(1); // Invalid ID, should be 0 for new reviews
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        Result<Reviews> result = service.add(review);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("ReviewId cannot be set for add operation"));
    }

    @Test
    void update() {
        Reviews review = new Reviews();
        review.setReviewId(1);
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        when(reviewsRepository.update(review)).thenReturn(true);

        Result<Reviews> result = service.update(review);
        assertTrue(result.isSuccess());
        assertEquals(review, result.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Reviews review = new Reviews();
        review.setReviewId(0); // Invalid ID, should not be 0 for existing reviews
        review.setAppointmentId(1);
        review.setCustomerId(1);
        review.setDriverId(1);
        review.setReviewText("Great service!");
        review.setRating(5);

        Result<Reviews> result = service.update(review);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("ReviewId must be set."));
    }

    @Test
    void deleteById() {
        Reviews review = new Reviews();
        review.setReviewId(1);

        when(reviewsRepository.deleteById(review.getReviewId())).thenReturn(true);

        Result<Reviews> result = service.deleteById(review);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDelete() {
        Reviews review = new Reviews();
        review.setReviewId(0); // Invalid ID, should not be 0 for existing reviews

        Result<Reviews> result = service.deleteById(review);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("ReviewId must be set."));
    }

}