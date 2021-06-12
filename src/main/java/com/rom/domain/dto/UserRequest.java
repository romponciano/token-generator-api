package com.rom.domain.dto;

import java.util.Objects;

public class UserRequest {
    private String username;
    private String newUsername;
    private String password;
    private String newPassword;

    public UserRequest(String username, String newUsername, String password, String newPassword) {
        this.username = username;
        this.newUsername = newUsername;
        this.password = password;
        this.newPassword = newPassword;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequest)) return false;
        UserRequest that = (UserRequest) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getNewUsername(), that.getNewUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getNewPassword(), that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getNewUsername(), getPassword(), getNewPassword());
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "username='" + username + '\'' +
                ", newUsername='" + newUsername + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
