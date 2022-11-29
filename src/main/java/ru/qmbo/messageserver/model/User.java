package ru.qmbo.messageserver.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * User
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Accessors(chain = true)
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
}
