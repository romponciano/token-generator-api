package com.rom.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Document
public class User {
    @Id
    private String username;
    private String password;
    private HashMap<String, Model> models;

    public User() {
    }

    public User(String username, String password, HashMap<String, Model> models) {
        this.username = username;
        this.password = password;
        this.models = models;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Model> getModels() {
        return models;
    }

    public void setModels(HashMap<String, Model> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getModels(), user.getModels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getModels());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", models=" + models +
                '}';
    }
}
