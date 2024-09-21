package learn.security;

import learn.data.AppUserRepository;
import learn.domain.Result;
import learn.domain.ResultType;
import learn.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder encoder) {
        this.appUserRepository = appUserRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }

    public AppUser findByUserName(String username) {
        return appUserRepository.findByUserName(username);
    }

    public AppUser findAppUserById(int userId) {
        return appUserRepository.findAppUserById(userId);
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public Result<AppUser> add(AppUser user) {
        Result<AppUser> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        if (appUserRepository.findByUserName(user.getUsername()) != null) {
            result.addMessage("Username already exists.", ResultType.INVALID);
            return result;
        }

        result.setPayload(appUserRepository.add(user));
        return result;
    }

    private Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null.", ResultType.INVALID);
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username is required.", ResultType.INVALID);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addMessage("Password is required.", ResultType.INVALID);
        }

        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            result.addMessage("At least one authority is required.", ResultType.INVALID);
        }

        if (user.getPassword().length() < 8) {
            result.addMessage("Password must be at least 8 characters long.", ResultType.INVALID);
        }

        return result;
    }

    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validate(user);

        if (user.getAppUserId() <= 0) {
            result.addMessage("Driver ID must be set.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        if (appUserRepository.findAppUserById(user.getAppUserId()) == null) {
            result.addMessage("User ID not found.", ResultType.NOT_FOUND);
            return result;
        }

        appUserRepository.update(user);
        result.setPayload(user);
        return result;
    }

    public boolean deleteById(int userId) {
        return appUserRepository.deleteById(userId);
    }

}
