package com.alexkekiy.common.utilites;

import com.alexkekiy.common.data.Account;
import lombok.Getter;
import lombok.Setter;

/**
 * дата-класс-обертка для ответа сервера
 */
@Getter
@Setter

public class Response extends Message {
    Account user;
    private boolean success;
    private boolean flag = true;
}
