package com.rom;

import com.google.gson.Gson;
import com.rom.domain.entity.Model;
import com.rom.domain.entity.User;
import com.rom.domain.repository.UserRepository;
import com.rom.domain.service.UserServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class UserTest {

    @Autowired private UserServiceImpl service;
    @Autowired private UserRepository repository;
    @Autowired private WebApplicationContext wac;

    private MockMvc mvc;
    private User user;
    private String json;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        json = Utils.resource("user.json");
        user = gson.fromJson(json, User.class);
        repository.deleteAll();
    }

    @Test
    void return_id_when_successfulLogin() throws Exception {
        String res = performPost("/tg/user/login", gson.toJson(user))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(res.equals(user.getId()));
    }

    private ResultActions performPost(String path, String json) throws Exception {
        return mvc.perform(
                post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
    }
}
