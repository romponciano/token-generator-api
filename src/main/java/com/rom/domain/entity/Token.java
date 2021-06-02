package com.rom.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Token {
    @Id
    private String id;
    private String modelId;
    private List<Field> fields;

    public Token(String id, String modelId, List<Field> fields) {
        this.id = id;
        this.modelId = modelId;
        this.fields = fields;
    }

    public Token() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return Objects.equals(getId(), token.getId()) && Objects.equals(getModelId(), token.getModelId()) && Objects.equals(getFields(), token.getFields());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModelId(), getFields());
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", modelId='" + modelId + '\'' +
                ", fields=" + fields +
                '}';
    }
}
