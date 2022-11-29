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
import ru.qmbo.messageserver.model.User;
import ru.qmbo.messageserver.repository.MessageRepository;
import ru.qmbo.messageserver.repository.UserRepository;
import ru.qmbo.messageserver.service.TokenService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @Test
    public void whenTokenOkAndStandardMessageThenWrite() throws Exception {
        when(messageRepository.save(any(Message.class))).thenReturn(new Message());
        when(userRepository.findByName(anyString())).thenReturn(Optional.of(new User().setId(100)));
        when(tokenService.checkToken(anyString(), anyString())).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(post("/messages")
                .header("Authorization", "Bearer_84884877Hdjdii")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"message\": \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[{\"message\":\"test\"}]");
    }


    @Test
    public void whenTokenOkAndGet10MessageThenGet10() throws Exception {
        when(messageRepository.getFirst10ByUserNameOrderByIdDesc(anyString()))
                .thenReturn(Collections.singletonList(new Message().setMessage("test")));
        when(tokenService.checkToken(anyString(), anyString())).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(post("/messages")
                .header("Authorization", "Bearer_84884877Hdjdii")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\", \"message\": \"history 10\"}"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[{\"message\":\"test\"}]");
    }

}