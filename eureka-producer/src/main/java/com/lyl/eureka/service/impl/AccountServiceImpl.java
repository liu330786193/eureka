package com.lyl.eureka.service.impl;

import com.lyl.eureka.dao.AccountDao;
import com.lyl.eureka.entity.Account;
import com.lyl.eureka.service.AccountService;
import com.tsign.cat.core.context.ContextManager;
import com.tsign.cat.plugin.tookit.trace.activation.annotation.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@ComponentScan
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Trace
    @Override
    public List<Account> selectList() {
        List<Account> list = accountDao.selectList();
        return list;
    }

    @Override
    public int truncateAccount() {
        return accountDao.truncateAccount();
    }

    @Trace
    @Override
    public int insert(Account record) {
        int a = accountDao.insert(record);
        selectList();
        return a;
    }
}
