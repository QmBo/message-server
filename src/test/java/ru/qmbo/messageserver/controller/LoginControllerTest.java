package ru.qmbo.messageserver.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;
import ru.qmbo.messageserver.model.LoginRequest;
import ru.qmbo.messageserver.model.LoginResponse;
import ru.qmbo.messageserver.model.User;
import ru.qmbo.messageserver.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Test
    public void whenPostRightLoginThenReturnToken() throws Exception {
        when(repository.findUserByNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(new User().setName("test")));
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"password\": \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString().split(":")[1].length() > 10).isTrue();
    }


    @Test
    public void whenPostIncorrectLoginThenReturnEmptyToken() throws Exception {
        when(repository.findUserByNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"password\": \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString().split(":")[1].equals("\"\"}")).isTrue();
    }

}