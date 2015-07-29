package com.bearmaster.talk.model;

public class User {
    
    private String name;
    
    private String password;

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        String maskedPassword = password != null ? "****" : "null"; 
        return "User [name = '" + name + "', password = '" + maskedPassword + "']";
    }

}
