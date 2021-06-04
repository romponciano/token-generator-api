package com.rom.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Model {
    private List<Field> fields;
    private List<Token> tokens;

    public Model() {
        this.fields = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    public Model(List<Field> fields, List<Token> tokens) {
        this.fields = fields != null ? fields : new ArrayList<>();
        this.tokens = tokens != null ? tokens : new ArrayList<>();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return Objects.equals(getFields(), model.getFields()) && Objects.equals(getTokens(), model.getTokens());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFields(), getTokens());
    }

    @Override
    public String toString() {
        return "Model{" +
                "fields=" + fields +
                ", tokens=" + tokens +
                '}';
    }
}
