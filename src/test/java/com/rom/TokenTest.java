package com.rom;

import com.google.gson.Gson;
import com.rom.domain.entity.Token;
import com.rom.domain.repository.TokenRepository;
import com.rom.domain.service.TokenServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class TokenTest {

    @Autowired private TokenServiceImpl service;
    @Autowired private TokenRepository repository;
    @Autowired private WebApplicationContext wac;

    private MockMvc mvc;
    private Token token;
    private String json;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        json = Utils.resource("token.json");
        token = gson.fromJson(json, Token.class);
        repository.deleteAll();
    }

    @Test
    void return_saved_when_postCorrectToken() throws Exception {
        String response = performPost(json)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(token.equals(gson.fromJson(response, Token.class)));
    }

    @Test
    void return_updated_when_postExistingToken() throws Exception {
        repository.save(token);

        token.setModelId("new name");
        String response = performPost(gson.toJson(token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(token.equals(gson.fromJson(response, Token.class)));
    }

    @Test
    void return_savedWithId_when_postModelNullId() throws Exception {
        token.setId(null);

        String response = performPost(gson.toJson(token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        Token modelResponse = gson.fromJson(response, Token.class);
        assertNotNull(modelResponse.getId());
        assertNotSame(token.getId(), modelResponse.getId());
        assertEquals(token.getModelId(), modelResponse.getModelId());
        assertEquals(token.getUserId(), modelResponse.getUserId());
        assertEquals(modelResponse.getTokens(), token.getTokens());
    }

    @Test
    void return_badRequest_when_saveWithoutUserId() throws Exception {
        token.setUserId(null);

        performPost(gson.toJson(token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_saveWithoutModelId() throws Exception {
        token.setModelId(null);

        performPost(gson.toJson(token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_saveWithoutUserIdAndModelId() throws Exception {
        token.setUserId(null);
        token.setModelId(null);

        performPost(gson.toJson(token))
                .andExpect(status().isBadRequest());
    }

    private ResultActions performPost(String json) throws Exception {
        return mvc.perform(
                post("/tg/token/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
    }

    @Test
    void return_correct_when_getWithValidId() throws Exception {
        repository.save(token);

        String response = mvc
                .perform(get("/tg/token/" + token.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(token.equals(gson.fromJson(response, Token.class)));
    }

    @Test
    void return_notFound_when_getWithInvalidId() throws Exception {
        mvc.perform(get("/tg/token/invalid-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_ok_whenDeleteValid() throws Exception {
        repository.save(token);

        mvc.perform(delete("/tg/token/" + token.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void return_ok_whenDeleteInvalid() throws Exception {
        mvc.perform(delete("/tg/token/invalid-id"))
                .andExpect(status().isOk());
    }

    @Test
    void return_correct_when_getByModelId() throws Exception {
        String userId = token.getUserId();
        String modelId = token.getModelId();
        int validSize = 4;
        for(int i=0; i < validSize; i++) {
            Token m = new Token();
            m.setUserId(userId);
            m.setModelId(modelId);
            repository.save(m);
        }
        Token t2 = new Token();
        t2.setUserId("outo id");
        t2.setModelId("name3");
        repository.save(t2);
        Token t3 = new Token();
        t3.setUserId("outo id 3");
        t3.setModelId("name4");
        repository.save(t3);

        String res = mvc
                .perform(get("/tg/token/model/" + modelId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertFalse(res.contains("name3"));
        assertFalse(res.contains("name4"));

        assertTrue(gson.fromJson(res, List.class).size() == validSize);
    }

    @Test
    void return_notFound_whenNoneUser() throws Exception {
        mvc.perform(get("/tg/token/user/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_correct_when_getByUserId() throws Exception {
        String userId = token.getUserId();
        String modelId = token.getModelId();
        int validSize = 4;
        for(int i=0; i < validSize; i++) {
            Token m = new Token();
            m.setUserId(userId);
            m.setModelId(modelId);
            repository.save(m);
        }
        Token t2 = new Token();
        t2.setUserId("outo id");
        t2.setModelId("name3");
        repository.save(t2);
        Token t3 = new Token();
        t3.setUserId("outo id 3");
        t3.setModelId("name4");
        repository.save(t3);

        String res = mvc
                .perform(get("/tg/token/user/" + userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertFalse(res.contains("outro id"));
        assertFalse(res.contains("outro id 3"));

        assertTrue(gson.fromJson(res, List.class).size() == validSize);
    }

    @Test
    void return_notFound_whenNoneModel() throws Exception {
        mvc.perform(get("/tg/token/model/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_ok_when_deleteByModelId() throws Exception {
        String userId = token.getUserId();
        String modelId = token.getModelId();
        int validSize = 4;
        for(int i=0; i < validSize; i++) {
            Token m = new Token();
            m.setUserId(userId);
            m.setModelId(modelId);
            repository.save(m);
        }
        Token t2 = new Token();
        t2.setUserId("outo id");
        t2.setModelId("name3");
        repository.save(t2);
        Token t3 = new Token();
        t3.setUserId("outo id 3");
        t3.setModelId("name4");
        repository.save(t3);

        mvc.perform(delete("/tg/token/model/" + modelId))
                .andExpect(status().isOk());

        assertTrue(repository.findByModelId(modelId).size() == 0);
    }

    @Test
    void return_ok_when_deleteByUserId() throws Exception {
        String userId = token.getUserId();
        String modelId = token.getModelId();
        int validSize = 4;
        for(int i=0; i < validSize; i++) {
            Token m = new Token();
            m.setUserId(userId);
            m.setModelId(modelId);
            repository.save(m);
        }
        Token t2 = new Token();
        t2.setUserId("outo id");
        t2.setModelId("name3");
        repository.save(t2);
        Token t3 = new Token();
        t3.setUserId("outo id 3");
        t3.setModelId("name4");
        repository.save(t3);

        mvc.perform(delete("/tg/token/user/" + userId))
                .andExpect(status().isOk());

        assertTrue(repository.findByUserId(modelId).size() == 0);
    }
}
