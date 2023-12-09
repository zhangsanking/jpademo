package com.example.jpademo.repositry;

import com.example.jpademo.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//@CacheConfig(cacheNames = "users")
@Repository
public interface UserDao extends JpaRepository<User,Long> {

   // @Cacheable(key = "#p0")
    User findByName(String name);

    @Query(value = "select * from user where id=(select max(id) from user)", nativeQuery = true)
    User findMaxIdUser();



}
