package com.alexkekiy.server.data.entities;

import com.alexkekiy.common.data.Account;
import com.alexkekiy.server.main.managers.DataBaseManager;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * дата-энтити класс синхронизированного с бд аккаунта
 */
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "login")
})
public class ServerAccount extends Account {


    @Getter
    @Transient
    public static ServerAccount commonAcc;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;
    @Getter
    @Transient
    private DataBaseManager dataBaseManager;
    public ServerAccount() {
        super();
    }
    public ServerAccount(String login, String password) {
        super(login, password);
        this.dataBaseManager = new DataBaseManager();
    }
}

