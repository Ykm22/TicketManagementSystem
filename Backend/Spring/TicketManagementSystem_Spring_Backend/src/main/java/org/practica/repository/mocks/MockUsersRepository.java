package org.practica.repository.mocks;

import org.practica.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MockUsersRepository {
    private List<User> users;

    public MockUsersRepository() {
        AddMockUsers();
    }

    private void AddMockUsers() {
        users = Arrays.asList(GetUser1(), GetUser2());
    }
    public static User GetUser1(){
        User u1 = new User();
        u1.setId(UUID.fromString("9DDC7E66-A5A0-45B7-81BA-C13971643212"));
        u1.setSalt("");
        u1.setPasswordHash("");
        u1.setEmail("mock_user1@email.com");
        u1.setIsCustomer(true);
        u1.setSex('m');
        u1.setAge(Short.valueOf("18"));
        return u1;
    }
    public static User GetUser2(){
        User u2 = new User();
        u2.setId(UUID.fromString("7E954890-CE11-40E5-AEBB-541FE169ED14"));
        u2.setSalt("");
        u2.setPasswordHash("");
        u2.setEmail("mock_user2@email.com");
        u2.setIsCustomer(true);
        u2.setSex('f');
        u2.setAge(Short.valueOf("20"));
        return u2;
    }

    public Optional<User> findById(UUID id) {
//        System.out.println("UsersRepository - findById(id)");
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
//        System.out.println("For id:" + id + " found: " + user.get());
//        System.out.println();
        return user;
    }

    public boolean existsById(UUID id) {
        return false;
    }

    public String findSaltByEmail(String email) {
        return "";
    }

    public String findHashByEmail(String email) {
        return "";
    }

    public void save(User user) {
        users.add(user);
    }
}
