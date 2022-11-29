package ru.qmbo.messageserver.service;

import org.springframework.stereotype.Service;
import ru.qmbo.messageserver.model.LoginRequest;
import ru.qmbo.messageserver.model.LoginResponse;
import ru.qmbo.messageserver.model.User;
import ru.qmbo.messageserver.repository.UserRepository;

import java.util.concurrent.atomic.AtomicReference;

/**
 * LoginService
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Service
public class UserService {

    private final UserRepository repository;
    private final TokenService tokenService;

    /**
     * Instantiates a new Login service.
     *
     * @param repository   the repository
     * @param tokenService the token service
     */
    public UserService(UserRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    /**
     * Login user on server and return him token.
     *
     * @param request the request
     * @return the token
     */
    public LoginResponse loginUser(LoginRequest request) {
        AtomicReference<String> token = new AtomicReference<>("");
        this.repository.findUserByNameAndPassword(request.getName(), request.getPassword())
                .ifPresent(user -> token.set(this.tokenService.getToken(user)));
        return new LoginResponse().setToken(token.get());
    }

    public User findUserByName(String name) {
        return this.repository.findByName(name).orElse(new User().setId(0));
    }
}
