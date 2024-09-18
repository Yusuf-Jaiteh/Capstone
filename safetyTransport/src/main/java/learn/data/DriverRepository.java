package learn.data;

import learn.models.Driver;

import java.util.List;

public interface DriverRepository {

    Driver findById(int id);

    List<Driver> findAll();

    Driver add(Driver driver);

    boolean update(Driver driver);

    boolean deleteById(int id);
}
