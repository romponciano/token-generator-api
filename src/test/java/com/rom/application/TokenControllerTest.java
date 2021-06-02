package com.rom.application;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.Token;
import com.rom.domain.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TokenController.class)
public class TokenControllerTest {
    @MockBean
    private TokenService service;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();
    private Token token;

    @Before
    public void setup() {
        token = gson.fromJson(Utils.resource("token.json"), Token.class);
    }

    @Test
    public void return_all_when_getAll_correct() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(token);
        tokens.add(token);
        tokens.add(token);
        Mockito.when(service.getAll()).thenReturn(tokens);

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tg/token"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(tokens.size(), (gson.fromJson(response, List.class)).size());
    }

    @Test
    public void return_correctModel_when_getById_correct() throws Exception {
        Mockito.when(service.getById(token.getId())).thenReturn(token);

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tg/token/" +  token.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(token, (gson.fromJson(response, Token.class)));
    }

    @Test
    public void return_created_when_post_correct() throws Exception {
        Mockito.when(service.create(any(Token.class))).thenReturn(token);
        Mockito.when(service.exists(token.getId())).thenReturn(false);

        String json = Utils.resource("token.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(token, gson.fromJson(response, Token.class));
    }

    @Test
    public void return_badRequest_when_post_existentId() throws Exception {
        Mockito.when(service.create(any(Token.class))).thenReturn(token);
        Mockito.when(service.exists(token.getId())).thenReturn(true);

        String json = Utils.resource("token.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_updated_when_put_correct() throws Exception {
        Mockito.when(service.update(any(Token.class))).thenReturn(token);
        Mockito.when(service.exists(token.getId())).thenReturn(true);

        String json = Utils.resource("token.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(token, gson.fromJson(response, Token.class));
    }

    @Test
    public void return_badRequest_when_put_inexistentId() throws Exception {
        Mockito.when(service.exists(token.getId())).thenReturn(false);

        String json = Utils.resource("token.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_delete_correct() throws Exception {
        Mockito.doNothing().when(service).deleteById(token.getId());
        Mockito.when(service.exists(token.getId())).thenReturn(true);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/token/" + token.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void return_badRequest_when_delete_inexistentId() throws Exception {
        Mockito.when(service.exists(token.getId())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/token/" + token.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
