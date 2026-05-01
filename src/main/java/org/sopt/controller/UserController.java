package org.sopt.controller;

import org.sopt.dto.request.CreateUserRequest;
import org.sopt.dto.request.UpdateUserRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.IdResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /users
    @PostMapping
    public ResponseEntity<ApiResponse<IdResponse>> createUser(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.createUser(request)));
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        return ApiResponse.success(userService.getUser(id));
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public ApiResponse<IdResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success(userService.updateUser(id, request));
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
