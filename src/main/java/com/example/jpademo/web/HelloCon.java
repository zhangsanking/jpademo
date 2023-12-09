package com.example.jpademo.web;

import com.example.jpademo.domain.User;
import com.example.jpademo.repositry.UserDao;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CacheConfig(cacheNames = "users")
public class HelloCon {
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserDao userDao;
    @RequestMapping("/find")
    @Cacheable(cacheNames ="users" ,key = "#p0"
    )
    public Optional<User> find(@RequestParam("id") Long id){
        System.out.println("没走缓存");
       return userDao.findById(id);
    }
    @CachePut (cacheNames = "users",key = "#user.id")
    @RequestMapping("/save")
    public User saveUser(User user){
       System.out.println(user);
       //cacheManager.getCache("users").put("users",user);
       return userDao.save(user);
    }

    @CacheEvict(cacheNames = "users",key = "#user.id")
    @RequestMapping("/update")
    public User update(User user) {
       return userDao.save(user);
    }

    @CacheEvict(cacheNames = "users",key = "#id")
    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestParam("id") Long id){
           userDao.deleteById(id);
    }
}
