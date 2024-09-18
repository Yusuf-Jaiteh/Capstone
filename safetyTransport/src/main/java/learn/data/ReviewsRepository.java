package learn.data;

import learn.models.Reviews;

import java.util.List;

public interface ReviewsRepository {

    Reviews findById(int id);
    List<Reviews> findAll();
    List<Reviews> findByDriverId(int driverId);
    List<Reviews> findByCustomerId(int customerId);
    Reviews add(Reviews review);
    boolean update(Reviews review);
    boolean deleteById(int id);
}
