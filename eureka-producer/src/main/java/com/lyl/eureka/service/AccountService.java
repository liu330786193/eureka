package com.lyl.eureka.service;

import com.lyl.eureka.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> selectList();

    int truncateAccount();

    int insert(Account record);

}
