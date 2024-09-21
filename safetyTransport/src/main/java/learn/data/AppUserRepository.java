package learn.data;

import learn.models.AppUser;

import java.util.List;

public interface AppUserRepository {

    List<AppUser> findAll();
    AppUser findAppUserById(int id);
    AppUser findByUserName(String username);
    AppUser add(AppUser appUser);
    boolean update(AppUser appUser);
    boolean deleteById(int id);


}
