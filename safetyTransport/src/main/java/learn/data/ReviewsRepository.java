package learn.data;

import learn.model.Reviews;

import java.util.List;

public interface ReviewsRepository {

    Reviews findById(int id);
    List<Reviews> findAll();
    Reviews add(Reviews review);
    boolean update(Reviews review);
    boolean deleteById(int id);
}
