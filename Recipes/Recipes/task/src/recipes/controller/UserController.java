package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.entity.User;
import recipes.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) == null
                && user.getEmail().matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$")
                && user.getPassword().trim().length() >= 8) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(userService.addUser(user));
        }

        return ResponseEntity.badRequest().build();
    }
}
