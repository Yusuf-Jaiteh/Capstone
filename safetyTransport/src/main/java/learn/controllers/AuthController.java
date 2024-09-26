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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@ConditionalOnWebApplication
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final AppUserService appUserService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager,
                          JwtConverter jwtConverter,
                          PasswordEncoder encoder, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtConverter = jwtConverter;
        this.encoder = encoder;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AppUser user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(token);
            if (authentication.isAuthenticated()) {
                AppUser appUser = (AppUser) authentication.getPrincipal();
                String role = appUser.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .findFirst()
                        .orElse("ROLE_USER");
                return new ResponseEntity<>(
                        Map.of("jwt", jwtConverter.getTokenFromUser(appUser), "userId", String.valueOf(appUser.getAppUserId()),
                                "role", role),
                        HttpStatus.OK);
            }
        } catch (AuthenticationException ex) {
            logger.info("Attempting to authenticate user: {}", user.getUsername());
            logger.info("Authentication successful for user: {}", user.getUsername());
            logger.error("Authentication failed for user: {}", user.getUsername(), ex);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.info("Attempting to authenticate user: {}", user.getUsername());
        logger.info("Authentication successful for user: {}", user.getUsername());
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@AuthenticationPrincipal AppUser user) {
        String role = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse("ROLE_USER");
        return new ResponseEntity<>(
                Map.of("jwt", jwtConverter.getTokenFromUser(user), "userId", String.valueOf(user.getAppUserId()),"role", role),
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
        String authorities = registrationDetails.get("authorities");
        String password = registrationDetails.get("password");
        String email = registrationDetails.get("email");
        String phoneNumber = registrationDetails.get("phoneNumber");
        String licenseNumber = registrationDetails.get("licenseNumber");
        String carModel = registrationDetails.get("carModel");
        String numberPlate = registrationDetails.get("numberPlate");
        String dob = registrationDetails.get("dob");
        String gender = registrationDetails.get("gender");
        String residentialAddress = registrationDetails.get("residentialAddress");
        String yearsOfExperience = registrationDetails.get("yearsOfExperience");
        String licenseExpiryDate = registrationDetails.get("licenseExpiryDate");

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(List.of(authorities));
        user.setEnabled(true);
        user.setLocked(false);

        Result<AppUser> result = appUserService.add(user);
        if (result.isSuccess()) {
            AppUser appUser = result.getPayload();
            String jwt = jwtConverter.getTokenFromUser(appUser);
            String role = appUser.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .findFirst()
                    .orElse("ROLE_USER");
            return ResponseEntity.ok(Map.of("jwt", jwt, "userId", String.valueOf(appUser.getAppUserId()),"role", role));
        } else {
            return ResponseEntity.badRequest().body(Map.of("errors", String.join(", ", result.getMessages())));
        }
    }
}