package ru.qmbo.messageserver.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.qmbo.messageserver.model.Message;
import ru.qmbo.messageserver.model.MessageRequest;
import ru.qmbo.messageserver.service.MessageService;

import java.util.Collections;
import java.util.List;

/**
 * MessageController
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService service;

    /**
     * Instantiates a new Message controller.
     *
     * @param service the service
     */
    public MessageController(MessageService service) {
        this.service = service;
    }

    /**
     * Write or return messages.
     *
     * @param request the request
     * @param auth    the auth
     * @return the list of messages
     */
    @PostMapping
    public List<Message> writeOrReturn(
            @RequestBody MessageRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        if (auth == null) {
            return Collections.emptyList();
        }
        return this.service.writeOrReturn(request, auth);
    }
}
