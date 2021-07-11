package com.kaikeba.dao.imp;

import com.kaikeba.bean.Express;
import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author JJH
 * @2021/7/2 0:42
 * 说明：
 */
public class UserDaoMysqlTest {

    BaseUserDao dao = new UserDaoMysql();

    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println("[{size:总数, day:新增}]");
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<User> findAll = dao.findAll(false,5,5);
        System.out.println(findAll);
    }

    @Test
    public void findByName() {
        List<User> findByName = dao.findByName("admin");
        System.out.println(findByName);

    }

    @Test
    public void findByIdNumber() {
        User findByIdNumber = dao.findByIdNumber("0:0:0:0:0:0:0:1");
        System.out.println(findByIdNumber);
    }

    @Test
    public void findByUserPhone() {
        User findByUserPhone = dao.findByUserPhone("13777861732");
        System.out.println(findByUserPhone);
    }

    @Test
    public void insert() {
        Timestamp timestramp;
        User u = new User("帅哥","321","168.1.1.1","13777861234",null,null);
        boolean insert = dao.insert(u);
        System.out.println(insert);
    }
    //插入一些来测试
    @Test
    public void insert2(){
        boolean insert = false;
        for(int i=0;i<10;i++){
            User user = new User("帅哥"+(i+1),"123"+i,"168.1.1."+i,"1377786123"+i,null,null);
            insert = dao.insert(user);
        }

    }

    @Test
    public void update() {
        User u = new User("帅哥","321","168.1.0.1","13777861234",null,null);
        boolean update = dao.update(2,u);
        System.out.println(update);

    }

    @Test
    public void updateLoginTime() {
        boolean updateLoginTime = dao.updateLoginTime(1);
        System.out.println(updateLoginTime);
    }

    @Test
    public void delete() {
        boolean delete = dao.delete(12);
        System.out.println(delete);
    }
}