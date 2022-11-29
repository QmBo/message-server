package ru.qmbo.messageserver.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.qmbo.messageserver.model.Message;
import ru.qmbo.messageserver.repository.MessageRepository;
import ru.qmbo.messageserver.service.TokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenTokenBadAndStandardMessageThenReturnEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/messages")
                .header("Authorization", "Bearer_84884877Hdjdii")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"message\": \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void whenNoTokenAndStandardMessageThenReturnEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"message\": \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[]");
    }
}