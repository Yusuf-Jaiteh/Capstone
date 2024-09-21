package learn.controllers;

import learn.domain.Result;
import learn.models.AppUser;
import learn.security.AppUserService;
import learn.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ConditionalOnWebApplication
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final AppUserService AppUserService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtConverter jwtConverter,
                          PasswordEncoder encoder, learn.security.AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtConverter = jwtConverter;
        this.encoder = encoder;
        AppUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AppUser user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(token);
            if(authentication.isAuthenticated()) {
                AppUser appUser = (AppUser) authentication.getPrincipal();
                return new ResponseEntity<>(
                        Map.of("jwt", jwtConverter.getTokenFromUser(appUser)),
                        HttpStatus.OK);
            }
        } catch(AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@AuthenticationPrincipal AppUser user) {
        return new ResponseEntity<>(
                Map.of("jwt", jwtConverter.getTokenFromUser(user)),
                HttpStatus.OK);
    }

    @PostMapping("/password")
    public void getPasswordHash(@RequestBody Map<String, String> values) {
        System.out.println(encoder.encode(values.get("password")));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> registrationDetails) {
        String firstName = registrationDetails.get("firstName");
        String lastName = registrationDetails.get("lastName");
        String username = registrationDetails.get("username");
        String password = registrationDetails.get("password");
        String confirmPassword = registrationDetails.get("confirmPassword");
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        //user.setConfirmPassword(confirmPassword);


        // Register user
        Result<AppUser> result = AppUserService.add(user);
        if (result.isSuccess()) {
            // Authenticate user
            AppUser appUser = result.getPayload();
            String jwt = jwtConverter.getTokenFromUser(appUser);
            return ResponseEntity.ok(Map.of("jwt", jwt));
        } else {
            return ResponseEntity.badRequest().body(Map.of("errors", String.join(", ", result.getMessages())));
        }
    }

}
