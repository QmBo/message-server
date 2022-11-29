package ru.qmbo.messageserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.qmbo.messageserver.model.Message;

import java.util.List;

/**
 * MessageRepository
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    /**
     * Gets first 10 messages by user name order by id desc.
     *
     * @param user_name the user name
     * @return the first 10 by user name order by id desc
     */
    List<Message> getFirst10ByUserNameOrderByIdDesc(String user_name);
}
