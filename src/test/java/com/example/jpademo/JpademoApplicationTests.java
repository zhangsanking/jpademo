package com.example.jpademo;

import com.example.jpademo.domain.User;
import com.example.jpademo.repositry.UserDao;
//import com.example.jpademo.repositry2.UserD;
import com.example.jpademo.service.impl.BloomFilterService;
import com.example.jpademo.service.impl.UserService;
import com.example.jpademo.web.HelloCon;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
class JpademoApplicationTests {
    @Autowired
    UserDao userDao;

    @Autowired
    RedisTemplate redisTemplate;
    //@Autowired
    // UserD userDao2;
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    HelloCon helloCon;

    @Autowired
    private UserService userService;

   @Autowired
   private BloomFilterService bloomFilterService;

    @Test
    public void myTest()  throws Exception {
           userService.save(new User("zs",99));
    }


    @Test
    public void test03() {
        long exceptionIn = 10000L;
        double falsePro = 0.01;
        RBloomFilter<Object> bloomFilter = bloomFilterService.create("myfilter",
                exceptionIn, falsePro);

        for (long i = 0; i < exceptionIn; i++) {
            bloomFilter.add("a" + i);
        }
        long c = bloomFilter.count();

        System.out.println(c);

        int c2 = 0;
        for (long i = 0; i < 1000; i++) {
            if (bloomFilter.contains("b" + i))
                c2++;
        }
        System.out.println("error count" + c2);
        System.out.println(bloomFilter.contains("x" + 1000l));
        System.out.println(bloomFilter.contains("x" + 10001l));
        System.out.println("Exception Insertions " + bloomFilter.getExpectedInsertions());
        System.out.println("容错率 " + bloomFilter.getFalseProbability());
        System.out.println("hash count" + bloomFilter.getHashIterations());
    }

    @Test
    public void test02() {
        long capacity = 10000l;
        BloomFilter<Long> bloomFilter =
                BloomFilter.create(Funnels.longFunnel(),
                        capacity, 0.01);
        for (long i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
        int c = 0;
        for (long i = capacity; i < capacity * 2; i++) {
            if (bloomFilter.mightContain(i)) {
                c++;
            }
        }
        System.out.println(c);

    }


    @Test
    public void test01() {

        //Optional<User> user = userDao.findById(70l);
//测试dev test merge dev to main
        //redisTemplate.opsForValue().set(70,user.get());
        System.out.println(redisTemplate.opsForValue().get(70));
        //  User user = helloCon.saveUser(new User("zs", 80));
        //  System.out.println(user);
        // System.out.println(helloCon.find(user.getId()));

        //   System.out.println("ttwo " + helloCon.find(user.getId()));
        // Optional<User> user=helloCon.find(50l);
        //   System.out.println(user);
        // helloCon.saveUser(new User("lisi9",90));
        // System.out.println(helloCon.find("lisi9"));
        // helloCon.saveUser(new User("lisi7",80));

        // User user= helloCon.find("lisi7");
        //  System.out.println(user);
        //System.out.println(helloCon.find("lisi7"));

    }


    @Test
    void contextLoads() throws Exception {


        Foo foo = new Foo();

        foo.method(4);
        foo.method(5);
        for (int i = 0; i < 100; i++) {
            int x = 0;
            //x++;
            new Thread(() -> foo.method(x)).start();
            Thread.sleep(1);
            foo.method(9);
        }
    }
}

class Foo {
    public void method(int x) {
        // x=1;
        System.out.println(Thread.currentThread().getName() + "   x=" + x);
        // Some code
    }
}


