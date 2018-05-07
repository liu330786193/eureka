package com.lyl.eureka.service;

import com.lyl.eureka.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> selectList();

    int truncateAccount();

    int insert(Account record);

    int errorA(Account record);

    int errorB(Account record);

    int errorAB(Account record);

}
