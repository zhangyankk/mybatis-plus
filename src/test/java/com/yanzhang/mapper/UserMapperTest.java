package com.yanzhang.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhang.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void xxx(){
        Page<User> page= new Page(1,1);
        //page.setSearchCount(true);
        Page<User> page1 = userMapper.selectPage(page, null);
        log.info("page1={},{}",page.getCurrent(),page.getTotal());
        System.out.println("page1="+page1.getRecords());

        page= new Page(2,1);

        page1 = userMapper.selectPage(page, null);
        log.info("page2={},{}",page.getCurrent(),page.getTotal());
        System.out.println("page2="+page1.getRecords());
    }


    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));


        //        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
//        userList.forEach(System.out::println);

        //总结：mybatis-plus乐观锁，如果版本不一致，没更新到不会报错。需要手动根据更新结果处理
        Runnable noArguments =()->{

            User user = userMapper.selectById(5);
            System.out.println("user,son=="+user);
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            user.setAge(25);
            int updateCount = userMapper.updateById(user);
            System.out.println("son="+updateCount);
            user = userMapper.selectById(5);
            System.out.println("user,son2="+user);
        };
        Thread thread=new Thread(noArguments);
        thread.setDaemon(false);
        thread.start();

        User user = userMapper.selectById(5);
        user.setAge(26);
        int updateCount=userMapper.updateById(user);
        System.out.println("main="+updateCount);
        user = userMapper.selectById(5);
        System.out.println("user="+user);

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("xxxxxxxxxxxxxxxxxxx");
    }

}
