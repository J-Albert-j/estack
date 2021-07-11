package com.kaikeba.service;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.dao.imp.UserDaoMysql;

import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/7/2 15:53
 * 说明：
 */
public class UserService {
    private static BaseUserDao dao = new UserDaoMysql();
    /**
     * 用于查询数据库中的全部用户（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 查询所有用户
     *
     * @param limit      是否分页的标记，true：分页，false：查询所有
     * @param offset     sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 用户的集合
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return
     */
    public static List<User> findByName(String userName) {
        return dao.findByName(userName);
    }

    /**
     * 根据身份证号查询用户
     *
     * @param userIdNumber 身份证号
     * @return 查询结果
     */
    public static User findByIdNumber(String userIdNumber) {
        return dao.findByIdNumber(userIdNumber);
    }

    /**
     * 根据手机号查询用户
     *
     * @param userPhone 手机号
     * @return
     */
    public static User findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 用户录入
     *
     * @param u 要录入的对象
     * @return 录入的结果
     */
    public static boolean insert(User u) {
        return dao.insert(u);
    }

    /**
     * 用户的修改
     *
     * @param id      要修改用户的id
     * @param newUser 新的用户对象
     * @return 修改的结果
     */
    public static boolean update(int id, User newUser) {
        return dao.update(id,newUser);
    }

    /**
     * 更新登录时间
     *
     * @param id
     * @return
     */
    public static boolean updateLoginTime(int id) {
        return dao.updateLoginTime(id);
    }

    /**
     * 根据id，删除单个用户信息
     *
     * @param id 要删除的用户信息
     * @return 删除结果
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
