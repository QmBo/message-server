package ru.qmbo.messageserver.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * MessageRequest
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Accessors(chain = true)
@Data
public class MessageRequest {
    private String name;
    private String message;
}
