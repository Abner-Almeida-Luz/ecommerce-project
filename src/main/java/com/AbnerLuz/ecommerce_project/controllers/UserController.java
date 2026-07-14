package com.AbnerLuz.ecommerce_project.controllers;

import com.AbnerLuz.ecommerce_project.dtos.AuthenticationRequest;
import com.AbnerLuz.ecommerce_project.dtos.RegisterRequest;
import com.AbnerLuz.ecommerce_project.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest data) {
        return ResponseEntity.ok(userService.login(data));
    }

    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody @Valid RegisterRequest data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(data));

    }

    @GetMapping("/find-user-by-login/{login}")
    public ResponseEntity findUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok().body(userService.findUserByLogin(login));
    }

    @GetMapping("/list-all-users")
    public ResponseEntity listAllUsers() {
        return ResponseEntity.ok().body(userService.listAllUsers());
    }

    @DeleteMapping("/delete-user-by-login/{id}")
    public ResponseEntity<Void> deleteUserByLogin(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
