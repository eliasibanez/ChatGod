package test.elias.ChatGod.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Get status from a user
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserModel> updateUserStatus(@PathVariable Long id, @RequestBody UserModel.UserStatus status) {
        UserModel updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }
}
