package com.kaikeba.dao;

import com.kaikeba.bean.Express;
import com.kaikeba.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {

    /**
     * 控制台页面显示：
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     *
     */
    List<Map<String,Integer>> console();

    /**
     * 用于查询所有快递（快递列表页面）
     * @param limit 是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 根据快递单号，查询快递信息
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    Express findByNumber(String number);

    /**
     * 根据快递取件码，查询快递信息
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    Express findByCode(String code);

    /**
     * 根据用户手机号码，查询他所有的快递信息
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findByUserPhone(String userPhone);

    /**
     * 根据录入人手机号码，查询录入的所有记录
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * 快递的修改
     * @param id 要修改的快递id
     * @param newExpress 新的快递对象（number，company,username,userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(int id,Express newExpress);

    /**
     * 更改快递的状态为1，表示取件完成
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean updateStatus(String code);

    /**
     * 根据id，删除单个快递信息
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    boolean delete(int id);

    /**
     * 查询一年内所有快递
     * @param userPhone
     * @return
     */
    List<Express> findAllAmongYearByPhone(String userPhone);

    /**
     * 查询一个月内所有快递
     * @param userPhone
     * @return
     */
    List<Express> findAllAmongMonthByPhone(String userPhone);

    /**
     * 根据用户手机号和快递状态查询所有快递
     * @param userPhone 手机号
     * @param status    快递状态
     * @return 查询的快递信息，手机号不存在时返回null
     */
    List<Express> findByUserPhoneAndStatus(String userPhone, int status);
}
