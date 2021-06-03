package com.rom.domain.entity;

import java.util.List;
import java.util.Objects;

public class Token {
    private List<Field> token;

    public Token(List<Field> token) {
        this.token = token;
    }

    public Token() {}

    public List<Field> getToken() {
        return token;
    }

    public void setToken(List<Field> token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return Objects.equals(getToken(), token1.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }

    @Override
    public String toString() {
        return "Token{" +
                "token=" + token +
                '}';
    }
}
