package org.practica.service.user_service;

import org.practica.model.User;
import org.practica.model.httpMethodsInput.UserLoginDto;
import org.practica.model.httpMethodsInput.UserRegistrationDto;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

public interface UsersService {
    Optional<User> findById(UUID id);
    boolean existsById(UUID id);

    void registerUser(UserRegistrationDto userRegistrationDto);

    boolean checkPassword(UserLoginDto loginDto) throws NoSuchAlgorithmException;

    UUID findId(UserLoginDto loginDto);
}
