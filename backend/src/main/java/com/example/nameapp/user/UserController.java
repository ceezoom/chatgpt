package com.example.nameapp.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "invalid"));
        }
        if (repository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "exists"));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // production should hash
        repository.save(user);
        return ResponseEntity.ok(Map.of("userId", user.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        return repository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of("userId", u.getId())))
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("message", "auth fail")));
    }
}
