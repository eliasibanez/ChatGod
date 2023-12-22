package test.elias.ChatGod.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.RegisteredUser;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody UserModel loginUser) {
        UserModel user = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisteredUser registeredUser) {
        try {
            logger.debug("API POST RECEIVED: Registering user with username: " + registeredUser.getUsername() + " and email: " + registeredUser.getEmail());
            UserModel user = userService.register(registeredUser.getUsername(), registeredUser.getPassword(), registeredUser.getEmail());
            return ResponseEntity.ok("User registered successfully -> " + " - Username: " + registeredUser.getUsername() + " - Email: " + registeredUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "Error registering this user -> " + " - Username: " + registeredUser.getUsername() + " - Email: " + registeredUser.getEmail());
        }

    }


}


