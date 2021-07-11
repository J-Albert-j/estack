package com.kaikeba.dao.imp;
//4.创建实现类 实现接口，编写抽象方法
//5.生成测试类，测试每个方法
import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/7/3 15:05
 * 说明：
 */
public class CourierDaoMysql implements BaseCourierDao {
    //所有SQL语句抽取成常量
    /**
     * 查询所有快递员（总数 + 新增）
     */
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) data_size," +
            "COUNT(TO_DAYS(ENROLLTIME)=TO_DAYS(NOW()) OR NULL) data_day " +
            " FROM COURIER";
    /**
     * 查询所有快递员
     */
    public static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    /**
     * 分页查询数据库中所有快递员
     */
    public static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    /**
     * 根据id查快递员
     */
    public static final String SQL_FIND_BY_ID = "SELECT * FROM COURIER WHERE ID=?";
    /**
     * 根据手机号查快递员
     */
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM COURIER WHERE COURIERPHONE=?";
    /**
     * 根据姓名查快递员
     */
    public static final String SQL_FIND_BY_NAME = "SELECT * FROM COURIER WHERE COURIERNAME=?";
    /**
     * 根据身份证号查快递员
     */
    public static final String SQL_FIND_BY_ID_NUMBER = "SELECT * FROM COURIER WHERE COURIERIDNUMBER=?";
    /**
     * 录入快递
     */
    public static final String SQL_INSERT = "INSERT INTO COURIER(COURIERNAME,COURIERPHONE,COURIERIDNUMBER,COURIERPASSWORD,COURIERSENDNUMBER,ENROLLTIME,LOGINTIME) VALUES(?,?,?,?,0,NOW(),NOW())";
    /**
     * 修改快递员
     */
    public static final String SQL_UPDATE = "UPDATE COURIER SET COURIERNAME=?,COURIERPHONE=?,COURIERIDNUMBER=?,COURIERPASSWORD=? WHERE ID=?";
    /**
     * 修改快递员派件数
     */
    public static final String SQL_UPDATE_SEND_NUMBER = "UPDATE COURIER SET COURIERSENDNUMBER=COURIERSENDNUMBER+? WHERE ID=?";
    /**
     * 修改快递员登录时间
     */
    public static final String SQL_UPDATE_LOGIN_TIME = "UPDATE COURIER SET LOGINTIME=NOW() WHERE ID=?";
    /**
     * 快递员的删除
     */
    public static final String SQL_DELETE = "DELETE FROM COURIER WHERE ID=?";

    /**
     * 用于查询数据库中的全部快递快递员（总数，当日新增）
     * 快递员（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            result = state.executeQuery();
            if (result.next()){
                int data_size = result.getInt("data_size");
                int data_day = result.getInt("data_day");
                Map<String,Integer> data1 = new HashMap<>();
                data1.put("data_size",data_size);
                data1.put("data_day",data_day);
                data.add(data1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 查询所有快递员
     *
     * @param limit      是否分页的标记，true：分页，false：查询所有
     * @param offset     sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递员的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            if (limit){
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            result = state.executeQuery();

            while (result.next()){
                int id = result.getInt("id");
                String courierName = result.getString("courierName");
                String courierPhone = result.getString("courierPhone");
                String courierIdNumber = result.getString("courierIdNumber");
                String courierPassword = result.getString("courierPassword");
                int courierSendNumber = result.getInt("courierSendNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");

                Courier c = new Courier(id, courierName, courierPhone, courierIdNumber, courierPassword, courierSendNumber, enrollTime, loginTime);
                data.add(c);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据id查快递员
     *
     * @param id
     * @return 查到的快递员，id不存在时返回null
     */
    @Override
    public Courier findById(int id) {
        ArrayList<Courier> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_ID);
            state.setInt(1, id);
            result = state.executeQuery();

            if (result.next()){
                String courierName = result.getString("courierName");
                String courierPhone = result.getString("courierPhone");
                String courierIdNumber = result.getString("courierIdNumber");
                String courierPassword = result.getString("courierPassword");
                int courierSendNumber = result.getInt("courierSendNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");
                Courier c = new Courier(id, courierName, courierPhone, courierIdNumber, courierPassword, courierSendNumber, enrollTime, loginTime);
                return c;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据姓名查快递员
     *
     * @param courierName 姓名
     * @return 查到的快递员，courierName不存在时返回null
     */
    @Override
    public Courier findByName(String courierName) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NAME);
            state.setString(1, courierName);
            result = state.executeQuery();

            if (result.next()){
                int id = result.getInt("id");
                String courierPhone = result.getString("courierPhone");
                String courierIdNumber = result.getString("courierIdNumber");
                String courierPassword = result.getString("courierPassword");
                int courierSendNumber = result.getInt("courierSendNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");
                Courier c = new Courier(id, courierName, courierPhone, courierIdNumber, courierPassword, courierSendNumber, enrollTime, loginTime);
                return c;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据手机号查快递员
     *
     * @param courierPhone 手机号
     * @return 查到的快递员，courierPhone不存在时返回null
     */
    @Override
    public Courier findByPhone(String courierPhone) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            state.setString(1, courierPhone);
            result = state.executeQuery();

            if (result.next()){
                int id = result.getInt("id");
                String courierName = result.getString("courierName");
                String courierIdNumber = result.getString("courierIdNumber");
                String courierPassword = result.getString("courierPassword");
                int courierSendNumber = result.getInt("courierSendNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");

                Courier c = new Courier(id, courierName, courierPhone, courierIdNumber, courierPassword, courierSendNumber, enrollTime, loginTime);
                return c;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据身份证号查快递员
     *
     * @param courierIdNumber 身份证号
     * @return 查到的快递员，courierIdNumber不存在时返回null
     */
    @Override
    public Courier findByIdNumber(String courierIdNumber) {
        ArrayList<Courier> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_ID_NUMBER);
            state.setString(1, courierIdNumber);
            result = state.executeQuery();

            if (result.next()){
                int id = result.getInt("id");
                String courierName = result.getString("courierName");
                String courierPhone = result.getString("courierPhone");
                String courierPassword = result.getString("courierPassword");
                int courierSendNumber = result.getInt("courierSendNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");
                Courier c = new Courier(id, courierName, courierPhone, courierIdNumber, courierPassword, courierSendNumber, enrollTime, loginTime);
                return c;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 快递员的录入
     *
     * @param c 要录入的对象
     * @return 录入的结果
     */
    @Override
    public boolean insert(Courier c) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);

            state.setString(1,c.getCourierName());
            state.setString(2,c.getCourierPhone());
            state.setString(3,c.getCourierIdNumber());
            state.setString(4,c.getCourierPassword());

            return (state.executeUpdate() > 0);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 快递员的修改
     *
     * @param id         要修改的快递员id
     * @param newCourier 新的快递员对象
     * @return 修改的结果
     */
    @Override
    public boolean update(int id, Courier newCourier) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;

        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newCourier.getCourierName());
            state.setString(2,newCourier.getCourierPhone());
            state.setString(3,newCourier.getCourierIdNumber());
            state.setString(4,newCourier.getCourierPassword());
            state.setInt(5,id);

            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 派件数修改
     *
     * @param id        快递员id
     * @param increment 新增的派件数
     * @return 是否修改成功
     */
    @Override
    public boolean updateSendNumber(int id, int increment) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;

        try {
            state = conn.prepareStatement(SQL_UPDATE_SEND_NUMBER);
            state.setInt(1,increment);
            state.setInt(2,id);

            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 更新登陆时间
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateLoginTime(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;

        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            state.setInt(1,id);

            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 根据id，删除单个快递员信息
     *
     * @param id 要删除的快递员信息
     * @return 删除结果
     */
    @Override
    public boolean delete(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1,id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}

