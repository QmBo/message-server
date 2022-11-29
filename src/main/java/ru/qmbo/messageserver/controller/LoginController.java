package ru.qmbo.messageserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.qmbo.messageserver.model.LoginRequest;
import ru.qmbo.messageserver.model.LoginResponse;
import ru.qmbo.messageserver.service.UserService;

/**
 * LoginController
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final UserService service;

    /**
     * Instantiates a new Login controller.
     *
     * @param service the service
     */
    public LoginController(UserService service) {
        this.service = service;
    }

    /**
     * Login to server.
     *
     * @param request the request
     * @return the token
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return this.service.loginUser(request);
    }
}
