package com.kaikeba.dao;

import com.kaikeba.bean.User;

import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/6/30 16:42
 * 说明：
 */
public interface BaseUserDao {
    /**
     * 用于查询数据库中的全部用户（总数，当日新增）
     * @return [{size:总数, day:新增}]
     */
    List<Map<String, Integer>> console();

    /**
     * 查询所有用户
     * @param limit 是否分页的标记，true：分页，false：查询所有
     * @param offset sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 用户的集合
     */
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    List<User> findByName(String userName);

    /**
     * 根据身份证号查询用户
     * @param userIdNumber 身份证号
     * @return 查询结果
     */
    User findByIdNumber(String userIdNumber);

    /**
     * 根据手机号查询用户
     * @param userPhone 手机号
     * @return
     */
    User findByUserPhone(String userPhone);

    /**
     * 用户录入
     * @param u 要录入的对象
     * @return 录入的结果
     */
    boolean insert(User u);

    /**
     * 用户的修改
     * @param id 要修改用户的id
     * @param newUser 新的用户对象
     * @return 修改的结果
     */
    boolean update(int id, User newUser);

    /**
     * 更新登录时间
     * @param id
     * @return
     */
    boolean updateLoginTime(int id);

    /**
     * 根据id，删除单个用户信息
     * @param id 要删除的用户信息
     * @return 删除结果
     */
    boolean delete(int id);
}
