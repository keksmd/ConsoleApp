package com.alexkekiy.common.utilites;


import com.alexkekiy.common.data.Account;

public interface Callable {
    Response calling(String[] args, String v, Account user);
}
