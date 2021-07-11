package com.kaikeba.dao.imp;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseCourierDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author JJH
 * @2021/7/3 16:26
 * 说明：
 */
public class CourierDaoMysqlTest {
    //BaseUserDao dao = new UserDaoMysql();
    BaseCourierDao dao = new CourierDaoMysql();
    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println("[{size:总数, day:新增}]");
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Courier> findAll = dao.findAll(false,5,5);
        System.out.println(findAll);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByName() {
    }

    @Test
    public void findByPhone() {
    }

    @Test
    public void findByIdNumber() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void updateSendNumber() {
    }

    @Test
    public void updateLoginTime() {
    }

    @Test
    public void delete() {
    }
}