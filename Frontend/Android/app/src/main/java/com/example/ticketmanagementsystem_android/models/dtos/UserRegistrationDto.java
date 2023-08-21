package com.example.ticketmanagementsystem_android.models.dtos;

public class UserRegistrationDto {
    private String email;
    private String password;
    private String sex;
    private int age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserRegistrationDto(String email, String password, String sex, int age) {
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.age = age;
    }
}
