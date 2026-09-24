package com.techvika.user.restcontroller;

import com.techvika.user.dto.UserRequest;
import com.techvika.user.dto.UserResponse;
import com.techvika.user.service.UserService;
import com.techvika.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserRestController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                                content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates all fields of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                                content = @Content(schema = @Schema(implementation = com.techvika.user.entity.User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already in use"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a user", description = "Partially updates specific fields of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User patched successfully",
                                content = @Content(schema = @Schema(implementation = com.techvika.user.entity.User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already in use"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<User> patchUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        User user = userService.patchUser(id, updates);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                content = @Content(schema = @Schema(implementation = com.techvika.user.entity.User.class)))
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned",
                                content = @Content(schema = @Schema(implementation = com.techvika.user.entity.User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

