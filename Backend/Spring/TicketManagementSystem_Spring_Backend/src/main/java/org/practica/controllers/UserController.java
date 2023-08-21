package org.practica.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.practica.model.AuthenticationResponse;
import org.practica.model.User;
import org.practica.model.dto.CustomMessage;
import org.practica.model.httpMethodsInput.UserLoginDto;
import org.practica.model.httpMethodsInput.UserRegistrationDto;
import org.practica.service.user_service.UsersService;
import org.practica.service.user_service.UsersServiceImpl;
import org.practica.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/tms/api/spring/users")
public class UserController {
    private final UsersService usersService;
    public UserController(UsersServiceImpl usersService){
        this.usersService = usersService;
    }

    @GetMapping(value = "{userId}")
    public User getUserById(@PathVariable UUID userId){
        Optional<User> user = usersService.findById(userId);
        return user.orElse(null);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
        try{
            usersService.registerUser(userRegistrationDto);
            return ResponseEntity.ok(new CustomMessage("User registered successfully"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomMessage("Error while registering user"));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto){
        try {
            boolean loginSuccessful = usersService.checkPassword(loginDto);
            if (loginSuccessful) {
                UUID userId = usersService.findId(loginDto);
                User user = usersService.findById(userId).orElse(null);
                return ResponseEntity.ok(new AuthenticationResponse(JwtUtil.generateToken(user), user.getId(), user.isCustomer()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new CustomMessage("Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomMessage("Error while logging in" + e.getMessage()));
        }
    }

}
