package test.elias.ChatGod.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Found the users",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class)) })
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a user by their ID")
    @ApiResponse(responseCode = "200", description = "Found the user",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class)) })
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "200", description = "Created the user",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class)) })
    @PostMapping("/new")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel createdUser = userService.createUser(user);
        if(createdUser == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(createdUser);
    }

    // Update a user
    @Operation(summary = "Update a user")
    @ApiResponse(responseCode = "200", description = "Updated the user",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class)) })
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @Operation(summary = "Delete a user")
    @ApiResponse(responseCode = "200", description = "Deleted the user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Get status from a user
    @Operation(summary = "Get status from a user")
    @ApiResponse(responseCode = "200", description = "Found the user",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserModel.class)) })
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserModel> updateUserStatus(@PathVariable Long id, @RequestBody UserModel.UserStatus status) {
        UserModel updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }
}
