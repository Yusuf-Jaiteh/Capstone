package learn.controllers;

import learn.domain.Result;
import learn.domain.ReviewService;
import learn.models.Reviews;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewService service;

    public ReviewsController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> findById(@PathVariable int reviewId) {
        Reviews review = service.findById(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(review);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> findByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(service.findByCustomerId(customerId));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Object> findByDriverId(@PathVariable int driverId) {
        return ResponseEntity.ok(service.findByDriverId(driverId));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Reviews review) {
        Result<Reviews> result = service.add(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> update(@PathVariable int reviewId, @RequestBody Reviews review) {
        if (reviewId != review.getReviewId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Reviews> result = service.update(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId, @RequestBody Reviews review) {
        if (reviewId != review.getReviewId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Reviews> result = service.deleteById(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
