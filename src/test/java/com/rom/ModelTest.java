package com.rom;

import com.google.gson.Gson;
import com.rom.domain.entity.Model;
import com.rom.domain.repository.ModelRepository;
import com.rom.domain.service.ModelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class ModelTest {

    @Autowired private ModelServiceImpl service;
    @Autowired private ModelRepository repository;
    @Autowired private WebApplicationContext wac;

    private MockMvc mvc;
    private Model model;
    private String json;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        json = Utils.resource("model.json");
        model = gson.fromJson(json, Model.class);
        repository.deleteAll();
    }

    @Test
    void return_saved_when_postCorrectModel() throws Exception {
        String response = performPost(json)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(model.equals(gson.fromJson(response, Model.class)));
    }

    @Test
    void return_updated_when_postExistingModel() throws Exception {
        repository.save(model);

        model.setName("new name");
        String response = performPost(gson.toJson(model))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(model.equals(gson.fromJson(response, Model.class)));
    }

    @Test
    void return_savedWithId_when_postModelNullId() throws Exception {
        model.setId(null);

        String response = performPost(gson.toJson(model))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        Model modelResponse = gson.fromJson(response, Model.class);
        assertNotNull(modelResponse.getId());
        assertNotSame(model.getId(), modelResponse.getId());
        assertEquals(model.getName(), modelResponse.getName());
        assertEquals(model.getUserId(), modelResponse.getUserId());
        assertEquals(modelResponse.getFields(), model.getFields());
    }

    @Test
    void return_badRequest_when_saveWithoutUserId() throws Exception {
        model.setUserId(null);

        performPost(gson.toJson(model))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_saveWithoutModelName() throws Exception {
        model.setName(null);

        performPost(gson.toJson(model))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_saveWithoutUserIdAndModelName() throws Exception {
        model.setUserId(null);
        model.setName(null);

        performPost(gson.toJson(model))
                .andExpect(status().isBadRequest());
    }

    private ResultActions performPost(String json) throws Exception {
        return mvc.perform(
                post("/tg/model/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void return_correct_when_getWithValidId() throws Exception {
        repository.save(model);

        String response = mvc
                .perform(get("/tg/model/" + model.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(model.equals(gson.fromJson(response, Model.class)));
    }

    @Test
    void return_notFound_when_getWithInvalidId() throws Exception {
        mvc.perform(get("/tg/model/invalid-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_correct_when_getByUserId() throws Exception {
        String userId = model.getUserId();
        int validSize = 4;
        for(int i=0; i < validSize; i++) {
            Model m = new Model();
            m.setUserId(userId);
            repository.save(m);
        }
        Model m2 = new Model();
        m2.setUserId("outo id");
        m2.setName("name3");
        repository.save(m2);
        Model m3 = new Model();
        m3.setUserId("outo id 3");
        m3.setName("name4");
        repository.save(m3);

        String res = mvc
                .perform(get("/tg/model/user/" + userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertFalse(res.contains("outro id"));
        assertFalse(res.contains("outro id 3"));

        assertTrue(gson.fromJson(res, List.class).size() == validSize);
    }

    @Test
    void return_notFound_whenNoneModel() throws Exception {
        mvc.perform(get("/tg/model/user/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_ok_whenDeleteValid() throws Exception {
        repository.save(model);

        mvc.perform(delete("/tg/model/" + model.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void return_ok_whenDeleteInvalid() throws Exception {
        mvc.perform(delete("/tg/model/invalid-id"))
                .andExpect(status().isOk());
    }
}
