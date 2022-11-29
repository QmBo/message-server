package ru.qmbo.messageserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.qmbo.messageserver.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenServiceTest {
    @Autowired
    private TokenService service;

    @Test
    public void whenPutValidTokenThenTrue() {
        String test = service.getToken(new User().setName("test"));
        assertThat(service.checkToken(test, "test")).isTrue();
    }
}