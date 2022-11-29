package ru.qmbo.messageserver.service;

import org.springframework.stereotype.Service;
import ru.qmbo.messageserver.model.Message;
import ru.qmbo.messageserver.model.MessageRequest;
import ru.qmbo.messageserver.model.User;
import ru.qmbo.messageserver.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageService
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Service
public class MessageService {
    private final static String HISTORY = "history 10";
    private final TokenService tokenService;
    private final MessageRepository repository;
    private final UserService userService;

    /**
     * Instantiates a new Message service.
     *  @param tokenService the token service
     * @param repository   the repository
     * @param userService  the user service
     */
    public MessageService(TokenService tokenService, MessageRepository repository, UserService userService) {
        this.tokenService = tokenService;
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * Write or return message list. But before check the token.
     *
     * @param request the request
     * @param auth    the auth
     * @return the list of messages
     */
    public List<Message> writeOrReturn(MessageRequest request, String auth) {
        List<Message> result = new ArrayList<>(100);
        if (auth.startsWith("Bearer_")) {
            String token = auth.replace("Bearer_", "");
            if (this.tokenService.checkToken(token, request.getName())) {
                result.addAll(this.operation(request));
            }
        }
        return result;
    }

    private List<Message> operation(MessageRequest request) {
        List<Message> result = new ArrayList<>(100);
        if (HISTORY.equals(request.getMessage())) {
            result = this.repository.getFirst10ByUserNameOrderByIdDesc(request.getName());
        } else {
            User user = this.userService.findUserByName(request.getName());
            if (user.getId() != 0) {
                Message message = new Message().setMessage(request.getMessage()).setUser(user);
                result.add(message);
                this.repository.save(message);
            }
        }
        return result;
    }
}
