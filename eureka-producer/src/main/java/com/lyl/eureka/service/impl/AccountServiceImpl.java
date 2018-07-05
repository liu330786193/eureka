package com.lyl.eureka.service.impl;

import com.lyl.eureka.dao.AccountDao;
import com.lyl.eureka.entity.Account;
import com.lyl.eureka.service.AccountService;
import com.tsign.cat.toolkit.trace.ActiveSpan;
import com.tsign.cat.toolkit.trace.Trace;
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
        testPrivateTrace();
        return a;
    }

    @Trace
    private void testPrivateTrace(){
        //...监控私有方法添加注解
    }

    @Trace
    public void testStaticTrace(){
        //监控静态方法添加注解
    }

    @Trace
    @Override
    public int errorA(Account record) {
        int a = accountDao.insert(record);
        return a;
    }

    @Trace
    @Override
    public int errorB(Account record) {
        testException();
        int a = accountDao.insert(record);
        return a;
    }

    @Trace
    @Override
    public int errorAB(Account record) {
        testException();
        int a = accountDao.insert(record);
        return a;
    }

    @Trace
    public void testException(){
        int i = 10 / 0;
    }
}
