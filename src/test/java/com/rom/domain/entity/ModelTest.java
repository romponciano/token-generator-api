package com.rom.domain.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    @Test
    public void create_emptyList_when_tryCreateNullFieldsAndTokens() {
        Model response = new Model();

        assertEquals(0, response.getFields().size());
        assertEquals(0, response.getTokens().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullFieldsAndTokens2nConstructor() {
        Model response = new Model(null, null);

        assertEquals(0, response.getFields().size());
        assertEquals(0, response.getTokens().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullFields() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token());
        tokens.add(new Token());

        Model response = new Model(null, tokens);

        assertEquals(0, response.getFields().size());
        assertEquals(tokens.size(), response.getTokens().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullTokens() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field());
        fields.add(new Field());

        Model response = new Model(fields, null);

        assertEquals(0, response.getTokens().size());
        assertEquals(fields.size(), response.getFields().size());
    }

    @Test
    public void create_correct_when_tryCreateCorrect() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field());
        fields.add(new Field());

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token());
        tokens.add(new Token());
        tokens.add(new Token());

        Model response = new Model(fields, tokens);

        assertEquals(tokens.size(), response.getTokens().size());
        assertEquals(fields.size(), response.getFields().size());
    }
}
