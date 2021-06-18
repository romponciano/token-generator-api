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
    private String userId;
    private String modelId;
    private List<Field> tokens;

    public Token(String id, String userId, String modelId, List<Field> tokens) {
        setId(id);
        setTokens(tokens);
        this.userId = userId;
        this.modelId = modelId;
    }

    public Token() {
        setId(null);
        setTokens(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null
            ? getUserId() + "-" + getModelId()
            : id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<Field> getTokens() {
        return tokens;
    }

    public void setTokens(List<Field> tokens) {
        this.tokens = tokens == null
            ? new ArrayList<>()
            : tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return Objects.equals(getId(), token1.getId()) && Objects.equals(getUserId(), token1.getUserId()) && Objects.equals(getModelId(), token1.getModelId()) && Objects.equals(getTokens(), token1.getTokens());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getModelId(), getTokens());
    }
}
