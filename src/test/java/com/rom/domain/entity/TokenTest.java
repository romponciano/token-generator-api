package com.rom.domain.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenTest {

    @Test
    public void create_emptyList_when_tryCreateNullFieldsAndTokens() {
        Token response = new Token();

        assertEquals(0, response.getToken().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullFieldsAndTokens2nConstructor() {
        Token response = new Token(null, "user", "model", null);

        assertEquals(0, response.getToken().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field());
        fields.add(new Field());

        Token response = new Token(null, "user", "model", fields);

        assertEquals(fields.size(), response.getToken().size());
    }
}
