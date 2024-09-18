package learn.domain;

import learn.data.ReviewsRepository;
import learn.models.Reviews;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewsRepository repository;

    public ReviewService(ReviewsRepository repository) {
        this.repository = repository;
    }

    public Reviews findById(int id) {
        return repository.findById(id);
    }

    public List<Reviews> findAll() {
        return repository.findAll();
    }

    public List<Reviews> findByCustomerId(int id) {
        return repository.findByCustomerId(id);
    }

    public List<Reviews> findByDriverId(int id) {
        return repository.findByDriverId(id);
    }

    public Result<Reviews> add(Reviews review) {
        Result<Reviews> result = validate(review);
        if(review.getReviewId() != 0) {
            result.addMessage("ReviewId cannot be set for add operation", ResultType.INVALID);
        }

        if(!result.isSuccess()){
            return result;
        }
        result.setPayload(repository.add(review));
        return result;
    }

    public Result<Reviews> update(Reviews reviews) {
        Result<Reviews> result = validate(reviews);
        if(reviews.getReviewId() <= 0) {
            result.addMessage("ReviewId must be set.", ResultType.INVALID);
        }

        if(!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(reviews);
        if(!success) {
            result.addMessage("Review Id" + reviews.getReviewId() + " not found.", ResultType.NOT_FOUND);
        } else{
            result.setPayload(reviews);
        }

        return result;
    }

    public Result<Reviews> deleteById(Reviews reviews) {
        Result<Reviews> result = new Result<>();

        if (reviews == null) {
            result.addMessage("Reviews cannot be null.", ResultType.INVALID);
            return result;
        }

        if(reviews.getReviewId() <= 0) {
            result.addMessage("ReviewId must be set.", ResultType.INVALID);
        }

        if(!repository.deleteById(reviews.getReviewId())) {
            result.addMessage("Review Id" + reviews.getReviewId() + " not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Reviews> validate(Reviews review) {
        Result<Reviews> result = new Result<>();

        if( review == null ){
            result.addMessage("Review cannot be null.", ResultType.INVALID);
            return result;
        }


        if(review.getAppointmentId() <= 0) {
            result.addMessage("AppointmentId must be set", ResultType.INVALID);
        }

        if(review.getDriverId() <= 0) {
            result.addMessage("DriverId must be set", ResultType.INVALID);
        }

        if(review.getCustomerId() <= 0) {
            result.addMessage("CustomerId must be set", ResultType.INVALID);
        }

        if(review.getReviewText() == null || review.getReviewText().isEmpty()) {
            result.addMessage("Review Text must be set.", ResultType.INVALID);
        }

        if(review.getRating() < 1 && review.getRating() > 5) {
            result.addMessage("The rating must be between 1 and 5 inclusive.", ResultType.INVALID);
        }

        return result;
    }
}
