package org.practica.repository;

import org.practica.model.User;
import org.practica.model.dto.SaltHash;
import org.practica.model.httpMethodsInput.UserLoginDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends CrudRepository<User, UUID> {
    Optional<User> findById(UUID uuid);

    boolean existsById(UUID uuid);
    @Query("SELECT u.salt FROM User u WHERE u.email = :email")
    String findSaltByEmail(String email);
    @Query("SELECT u.passwordHash FROM User u WHERE u.email = :email")
    String findHashByEmail(String email);

    @Query("SELECT NEW org.practica.model.dto.SaltHash(u.salt, u.passwordHash) FROM User u WHERE u.email = :email")
    SaltHash findSaltHashByEmail(String email);

    @Query("SELECT u.userId FROM User u WHERE u.email = :email")
    UUID findIdByEmail(String email);
    @Query("SELECT u.isCustomer FROM User u WHERE u.email = :email")
    boolean findRoleByEmail(String email);
}