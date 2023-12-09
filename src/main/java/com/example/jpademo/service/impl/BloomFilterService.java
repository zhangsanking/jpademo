package com.example.jpademo.service.impl;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class BloomFilterService {
    @Autowired
    private RedissonClient redissonClient;

    public <T> RBloomFilter<T> create(String filterName,long capacity,double errorRate){
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(capacity,errorRate);
        return bloomFilter;
    }
}
