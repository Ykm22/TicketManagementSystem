package org.practica.model.httpMethodsInput;

public class UserRegistrationDto {
    private String email;
    private String password;
    private char sex;
    private short age;

    public UserRegistrationDto(String email, String password, char sex, short age) {
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.age = age;
    }

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

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }
}
