package com.rom;

import com.google.gson.Gson;
import com.rom.domain.dto.UserRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class UserTest {

    @Autowired private UserServiceImpl service;
    @Autowired private UserRepository repository;
    @Autowired private WebApplicationContext wac;

    private MockMvc mvc;
    private User user;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String json = Utils.resource("user.json");
        user = gson.fromJson(json, User.class);
        repository.deleteAll();
    }

    @Test
    void return_id_when_successfulLogin() throws Exception {
        repository.save(user);

        String res = performPost("/tg/user/login", gson.toJson(user))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        User userRes = gson.fromJson(res, User.class);
        assertTrue(userRes.getId() != null);
        assertTrue(userRes.getPassword() == null);
        assertTrue(user.getUsername().equals(userRes.getUsername()));
    }

    @Test
    void return_badRequest_when_inexistentUsername() throws Exception {
        performPost("/tg/user/login", gson.toJson(user))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_wrongPassword() throws Exception {
        repository.save(user);
        user.setPassword("wrong password");

        performPost("/tg/user/login", gson.toJson(user))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_saved_when_correctUser() throws Exception {
        String res = performPost("/tg/user/", gson.toJson(user))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertTrue(user.equals(gson.fromJson(res, User.class)));
        assertTrue(user.equals(repository.findById(user.getId()).get()));
    }

    @Test
    void return_badRequest_when_usernameAlreadyExists() throws Exception {
        repository.save(user);
        user.setId("different id");

        performPost("/tg/user/", gson.toJson(user))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_idAlreadyExists() throws Exception {
        repository.save(user);
        user.setUsername("different username");

        performPost("/tg/user/", gson.toJson(user))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_idAndUsernameAlreadyExists() throws Exception {
        repository.save(user);

        performPost("/tg/user/", gson.toJson(user))
                .andExpect(status().isBadRequest());
    }

    private ResultActions performPost(String path, String json) throws Exception {
        return mvc.perform(
                post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));
    }

    @Test
    void return_forbidden_when_usernameAlreadyExists() throws Exception {
        repository.save(user);

        mvc.perform(get("/tg/user/" + user.getUsername() + "/exists"))
                .andExpect(status().isForbidden());
    }

    @Test
    void return_ok_when_usernameDoestExists() throws Exception {
        mvc.perform(get("/tg/user/" + user.getUsername() + "/exists"))
                .andExpect(status().isOk());
    }

    @Test
    void return_ok_when_deleteValidId() throws Exception {
        repository.save(user);

        mvc
            .perform(
                delete("/tg/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
            )
            .andExpect(status().isOk());
    }

    @Test
    void return_badRequest_when_deleteInvalidId() throws Exception {
        mvc
                .perform(
                        delete("/tg/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_deleteInvalidPassword() throws Exception {
        repository.save(user);

        user.setPassword("wrong pass");
        mvc
                .perform(
                        delete("/tg/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_ok_when_updateValidUsername() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setNewUsername("another username");
        String res = mvc
                .perform(
                        put("/tg/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        User response = gson.fromJson(res, User.class);
        assertTrue(response.getUsername().equals(request.getNewUsername()));
        assertTrue(response.getPassword().equals(request.getPassword()));
        assertTrue(response.getId().equals(user.getId()));
    }

    @Test
    void return_ok_when_updateValidPassword() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setNewPassword("new pass");
        String res = mvc
                .perform(
                        put("/tg/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        User response = gson.fromJson(res, User.class);
        assertTrue(response.getUsername().equals(request.getUsername()));
        assertTrue(response.getPassword().equals(request.getNewPassword()));
        assertTrue(response.getId().equals(user.getId()));
    }

    @Test
    void return_ok_when_updateValidUsernameAndPassword() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setNewUsername("another username");
        request.setNewPassword("new pass");
        String res = mvc
                .perform(
                        put("/tg/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        User response = gson.fromJson(res, User.class);
        assertTrue(response.getUsername().equals(request.getNewUsername()));
        assertTrue(response.getPassword().equals(request.getNewPassword()));
        assertTrue(response.getId().equals(user.getId()));
    }

    @Test
    void return_badRequest_when_updateInvalidUsername() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername("invalid username");
        request.setPassword(user.getPassword());
        request.setNewUsername("another username");
        request.setNewPassword("new pass");

        mvc
                .perform(
                        put("/tg/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_updateInvalidPassword() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername(user.getUsername());
        request.setPassword("invalid pass");
        request.setNewUsername("another username");
        request.setNewPassword("new pass");

        mvc
                .perform(
                        put("/tg/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_badRequest_when_updateInvalidUsernameAndPassword() throws Exception {
        repository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername("invalid username");
        request.setPassword("invalid pass");
        request.setNewUsername("another username");
        request.setNewPassword("new pass");

        mvc
                .perform(
                        put("/tg/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request))
                )
                .andExpect(status().isBadRequest());
    }
}
