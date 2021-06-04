package com.rom.domain.entity;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void create_emptyList_when_tryCreateNullModals() {
        User response = new User();

        assertEquals(0, response.getModels().size());
    }

    @Test
    public void create_emptyList_when_tryCreateNullModals2ndConstructor() {
        User response = new User(null, null, null);

        assertEquals(0, response.getModels().size());
    }

    @Test
    public void create_correct_when_tryCreateCorrectModel() {
        HashMap<String, Model> models = new HashMap<>();
        models.put("M1", null);
        models.put("M2", null);
        models.put("M3", null);

        User response = new User(null, null, models);

        assertEquals(models.size(), response.getModels().size());
    }
}
