package com.alexkekiy.common.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static com.alexkekiy.common.utilites.PasswordCryptography.encodePassword;

@NoArgsConstructor
/**
 * дата-класс представляющий аккаунт - форма для хранения логина и пароля
 */

@Getter
@Setter
@MappedSuperclass
public class Account {

    @Getter
    @Setter
    private static Account commonAcc = new Account("common", encodePassword("0000"));
    @Basic
    public String login;
    @Basic
    public String password;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return this.getLogin().equals(account.getLogin()) && this.getPassword().equals(account.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLogin(), this.getPassword());
    }

}
