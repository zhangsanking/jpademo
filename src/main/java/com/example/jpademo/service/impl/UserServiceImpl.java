package com.example.jpademo.service.impl;

import com.example.jpademo.domain.User;
import com.example.jpademo.repositry.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void save(User user) throws Exception {

            userDao.save(user);
            //throw new SQLException("User saved successfully");

    }
}
