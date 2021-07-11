package com.kaikeba.dao;
//3.编写dao接口
import com.kaikeba.bean.Courier;

import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/7/3 15:00
 * 说明：
 */
public interface BaseCourierDao {
    /**
     * 用于查询数据库中的全部快递快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     * @return [{size:总数, day:新增}]
     */
    List<Map<String, Integer>> console();

    /**
     * 查询所有快递员
     * @param limit 是否分页的标记，true：分页，false：查询所有
     * @param offset sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递员的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据id查快递员
     * @param id
     * @return 查到的快递员，id不存在时返回null
     */
    Courier findById(int id);

    /**
     * 根据姓名查快递员
     * @param courierName 姓名
     * @return 查到的快递员，courierName不存在时返回null
     */
    Courier findByName(String courierName);

    /**
     * 根据手机号查快递员
     * @param courierPhone 手机号
     * @return 查到的快递员，courierPhone不存在时返回null
     */
    Courier findByPhone(String courierPhone);

    /**
     * 根据身份证号查快递员
     * @param courierIdNumber 身份证号
     * @return 查到的快递员，courierIdNumber不存在时返回null
     */
    Courier findByIdNumber(String courierIdNumber);

    /**
     * 快递员的录入
     * @param c 要录入的对象
     * @return 录入的结果
     */
    boolean insert(Courier c);

    /**
     * 快递员的修改
     * @param id 要修改的快递员id
     * @param newCourier 新的快递员对象
     * @return 修改的结果
     */
    boolean update(int id, Courier newCourier);

    /**
     * 派件数修改
     * @param id 快递员id
     * @param increment 新增的派件数
     * @return 是否修改成功
     */
    boolean updateSendNumber(int id, int increment);

    /**
     * 更新登陆时间
     * @param id
     * @return
     */
    boolean updateLoginTime(int id);

    /**
     * 根据id，删除单个快递员信息
     * @param id 要删除的快递员信息
     * @return 删除结果
     */
    boolean delete(int id);
}
