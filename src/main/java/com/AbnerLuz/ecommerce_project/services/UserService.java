package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.Carts;
import com.AbnerLuz.ecommerce_project.core.Users;
import com.AbnerLuz.ecommerce_project.dtos.AuthenticationRequest;
import com.AbnerLuz.ecommerce_project.dtos.LoginResponse;
import com.AbnerLuz.ecommerce_project.dtos.RegisterRequest;
import com.AbnerLuz.ecommerce_project.dtos.UserResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.infra.security.TokenService;
import com.AbnerLuz.ecommerce_project.repositories.CartRepository;
import com.AbnerLuz.ecommerce_project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserResponse toResponse(Users user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getLogin(),
            user.getRole()
        );
    }

    public LoginResponse login(AuthenticationRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return new LoginResponse(token);
    }

    @Transactional
    public UserResponse createUser(RegisterRequest data) {
        Users user = new Users(data.username(),data.login(), passwordEncoder.encode((data.password())), data.role());
        userRepository.save(user);
        Carts cart = new Carts(user);
        cartRepository.save(cart);

        return toResponse(user);
    }

    public UserResponse findUserByLogin(String login) {
        Users user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        return toResponse(user);
    }

    public List<UserResponse> listAllUsers() {
        return userRepository.findAll().stream().map(
                this::toResponse
        ).toList();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
