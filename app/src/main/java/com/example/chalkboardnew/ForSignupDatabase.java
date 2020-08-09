package com.example.chalkboardnew;

public class ForSignupDatabase {
    String username, email, password,choice;

    public ForSignupDatabase() {

    }

    public ForSignupDatabase(String username, String email, String password,String choice) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.choice = choice;

    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
