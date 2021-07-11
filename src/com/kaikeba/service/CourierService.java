package com.kaikeba.service;
//6.编写sevice调用dao
import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.imp.CourierDaoMysql;

import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/7/3 15:13
 * 说明：
 */
public class CourierService {
    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 用于查询数据库中的全部快递快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 查询所有快递员
     *
     * @param limit      是否分页的标记，true：分页，false：查询所有
     * @param offset     sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递员的集合
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据id查快递员
     *
     * @param id
     * @return 查到的快递员，id不存在时返回null
     */
    public static Courier findById(int id) {
        return dao.findById(id);
    }

    /**
     * 根据姓名查快递员
     *
     * @param courierName 姓名
     * @return 查到的快递员，courierName不存在时返回null
     */
    public static Courier findByName(String courierName) {
        return dao.findByName(courierName);
    }

    /**
     * 根据手机号查快递员
     *
     * @param courierPhone 手机号
     * @return 查到的快递员，courierPhone不存在时返回null
     */
    public static Courier findByPhone(String courierPhone) {
        return dao.findByPhone(courierPhone);
    }

    /**
     * 根据身份证号查快递员
     *
     * @param courierIdNumber 身份证号
     * @return 查到的快递员，courierIdNumber不存在时返回null
     */
    public static Courier findByIdNumber(String courierIdNumber) {
        return dao.findByIdNumber(courierIdNumber);
    }

    /**
     * 快递员的录入
     *
     * @param c 要录入的对象
     * @return 录入的结果
     */
    public static boolean insert(Courier c) {
        return dao.insert(c);
    }

    /**
     * 快递员的修改
     *
     * @param id         要修改的快递员id
     * @param newCourier 新的快递员对象
     * @return 修改的结果
     */
    public static boolean update(int id, Courier newCourier) {
        return dao.update(id,newCourier);
    }

    /**
     * 根据id，删除单个快递员信息
     *
     * @param id 要删除的快递员信息
     * @return 删除结果
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * 派件数修改
     * @param id 快递员id
     * @param increment 新增的派件数
     * @return 是否修改成功
     */
    public static boolean updateSendNumber(int id, int increment){
        return dao.updateSendNumber(id, increment);
    }

    /**
     * 更新登陆时间
     * @param id
     * @return
     */
    public static boolean updateLoginTime(int id){
        return dao.updateLoginTime(id);
    }
}
