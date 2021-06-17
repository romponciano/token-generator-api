package com.rom.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Token {
    @Id
    private String id;
    private String username;
    private String modelId;
    private List<Field> token;

    public Token(String id, String username, String modelId, List<Field> token) {
        setId(id);
        setToken(token);
        this.username = username;
        this.modelId = modelId;
    }

    public Token() {
        setId(null);
        setToken(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null
            ? getUsername() + "-" + getModelId()
            : id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<Field> getToken() {
        return token;
    }

    public void setToken(List<Field> token) {
        this.token = token == null
            ? new ArrayList<>()
            : token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return Objects.equals(getId(), token1.getId()) && Objects.equals(getUsername(), token1.getUsername()) && Objects.equals(getModelId(), token1.getModelId()) && Objects.equals(getToken(), token1.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getModelId(), getToken());
    }
}
