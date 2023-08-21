package org.practica.service.user_service;

import org.apache.juli.logging.LogFactory;
import org.practica.model.User;
import org.practica.model.dto.SaltHash;
import org.practica.model.httpMethodsInput.UserLoginDto;
import org.practica.model.httpMethodsInput.UserRegistrationDto;
import org.practica.repository.UsersRepository;
import org.practica.repository.mocks.MockUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{
    private UsersRepository usersRepository;
//    private MockUsersRepository usersRepository;
    private final Logger logger;
    public UsersServiceImpl(
            UsersRepository usersRepository
//        MockUsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
        this.logger = LoggerFactory.getLogger(usersRepository.getClass());
    }

    @Override
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = getFromDto(userRegistrationDto);
        if(user == null){
            return;
        }
        logger.info("UsersRepository - save(user)");
        usersRepository.save(user);
    }

    private User getFromDto(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setAge(userRegistrationDto.getAge());
        user.setEmail(userRegistrationDto.getEmail());
        user.setSex(userRegistrationDto.getSex());

        String salt = UUID.randomUUID().toString();
        user.setSalt(salt);

        String password = userRegistrationDto.getPassword();
        String passwordHash = "";
        try{
            passwordHash = sha256Hash(password, salt);
        } catch(NoSuchAlgorithmException e){
            return null;
        }
        user.setPasswordHash(passwordHash);
        user.setId(UUID.randomUUID());
        user.setIsCustomer(true);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id){
        logger.info("UsersRepository - findById(id)");
        return usersRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id){
        logger.info("UsersRepository - existsById(id)");
        return usersRepository.existsById(id);
    }

    @Override
    public boolean checkPassword(UserLoginDto loginDto) throws NoSuchAlgorithmException {
        SaltHash saltHash = getSaltAndHash(loginDto);
        String saltedPassword = sha256Hash(loginDto.getPassword(), saltHash.getSalt());
        return saltedPassword.equalsIgnoreCase(saltHash.getHash());
    }

    @Override
    public UUID findId(UserLoginDto loginDto) {
        return usersRepository.findIdByEmail(loginDto.getEmail());
    }

    private SaltHash getSaltAndHash(UserLoginDto loginDto) {
//        logger.info("UsersRepository - findSaltByEmail(email)");
//        String salt = usersRepository.findSaltByEmail(loginDto.getEmail());
//        logger.info("UsersRepository - findHashByEmail(email)");
//        String passwordHash = usersRepository.findHashByEmail(loginDto.getEmail());
        return usersRepository.findSaltHashByEmail(loginDto.getEmail());
    }


    private static String sha256Hash(String password, String salt) throws NoSuchAlgorithmException {
        // Concatenate the password and salt
        String input = password + salt;

        // Create a SHA-256 MessageDigest object
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        // Calculate the hash value
        byte[] hashBytes = messageDigest.digest(input.getBytes());

        // Convert the byte array to a hexadecimal representation
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
