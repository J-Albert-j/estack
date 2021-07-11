package com.kaikeba.dao.imp;

import com.kaikeba.bean.Express;
import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/6/30 16:46
 * 说明：
 */
public class UserDaoMysql implements BaseUserDao {
    /**
     * 查询用户（总数+新增）
     */
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) data_size," +
            "COUNT(TO_DAYS(ENROLLTIME)=TO_DAYS(NOW()) OR NULL) data_day " +
            "FROM USER";
    /**
     * 查询数据库中所有的用户信息
     */
    public static final String SQL_FIND_ALL = "SELECT * FROM USER";
    /**
     * 分页查询数据库中所有用户信息
     */
    public static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";
    /**
     * 通过用户名查询用户信息
     */
    public static final String SQL_FIND_BY_USER_NAME = "SELECT * FROM USER WHERE USERNAME=?";
    /**
     * 通过手机号查询用户信息
     */
    public static final String SQL_FIND_BY_USER_PHONE = "SELECT * FROM USER WHERE USERPHONE=?";

    public static final String SQL_FIND_BY_USER_ID_NUMBER = "SELECT * FROM USER WHERE USERIDNUMBER=?";
    /**
     * 录入用户
     */
    public static final String SQL_INSERT = "INSERT INTO USER(USERNAME,USERPHONE,USERIDNUMBER,USERPASSWORD,ENROLLTIME,LOGINTIME) VALUES(?,?,?,?,NOW(),NOW())";
    /**
     * 修改用户
     */
    public static final String SQL_UPDATE = "UPDATE USER SET USERNAME=?,USERPHONE=?,USERIDNUMBER=?,USERPASSWORD=? WHERE ID=?";
    /**
     * 修改用户登录时间
     */
    public static final String SQL_UPDATE_LOGIN_TIME = "UPDATE USER SET LOGINTIME=NOW() WHERE ID=?";
    /**
     * 用户的删除
     */
    public static final String SQL_DELETE = "DELETE FROM USER WHERE ID=?";


    /**
     * 用于查询数据库中的全部用户（总数，当日新增）
     *
     * @return [{size:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.    填充参数(可选)
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果[{size:总数, day:新增}]
            if (result.next()){
                int data_size = result.getInt("data_size");
                int data_day = result.getInt("data_day");
                Map<String,Integer> data1 = new HashMap<>();
                data1.put("data_size",data_size);
                data1.put("data_day",data_day);
                data.add(data1);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }

        return data;
        
    }

    /**
     * 查询所有用户
     *
     * @param limit      是否分页的标记，true：分页，false：查询所有
     * @param offset     sql语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 用户的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            if(limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数(可选)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while(result.next()){
                int id = result.getInt("id");
                String userName = result.getString("userName");
                String userPassword = result.getString("userPassword");
                String userPhone = result.getString("userPhone");
                String userIdNumber = result.getString("userIdNumber");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");
                User user = new User(id,userName,userPassword,userIdNumber,userPhone,enrollTime,loginTime);
                data.add(user);
//                private int id;
//                private String userName;
//                private String userPassword;
//                private String userIdNumber;
//                private String userPhone;
//                private Timestamp enrollTime;
//                private Timestamp loginTime;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public List<User> findByName(String userName) {
        ArrayList<User> data = new ArrayList<>();
        PreparedStatement state = null;
        ResultSet result =null;
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_FIND_BY_USER_NAME);
            //3.    填充参数(可选)
            state.setString(1,userName);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while (result.next()){
                int id = result.getInt("id");
                String userPhone = result.getString("userPhone");
                String userIdNumber = result.getString("userIdNumber");
                String userPassword = result.getString("userPassword");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");

                User u = new User(id, userName, userPassword, userIdNumber, userPhone, enrollTime, loginTime);//需要与全参构造方法中顺序一致
                data.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据身份证号查询用户
     *
     * @param userIdNumber 身份证号
     * @return 查询结果
     */
    @Override
    public User findByIdNumber(String userIdNumber) {
        PreparedStatement state = null;
        ResultSet result =null;
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_FIND_BY_USER_ID_NUMBER);
            //3.    填充参数(可选)
            state.setString(1,userIdNumber);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()){
                int id = result.getInt("id");
                String userName = result.getString("userName");
                String userPhone = result.getString("userPhone");
                String userPassword = result.getString("userPassword");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");

                User u = new User(id, userName, userPassword, userIdNumber, userPhone, enrollTime, loginTime);
                return u;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据手机号查询用户
     *
     * @param userPhone 手机号
     * @return
     */
    @Override
    public User findByUserPhone(String userPhone) {
        PreparedStatement state = null;
        ResultSet result =null;
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_FIND_BY_USER_PHONE);
            //3.    填充参数(可选)
            state.setString(1,userPhone);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()){
                int id = result.getInt("id");
                String userName = result.getString("userName");
                String userIdNumber = result.getString("userIdNumber");
                String userPassword = result.getString("userPassword");
                Timestamp enrollTime = result.getTimestamp("enrollTime");
                Timestamp loginTime = result.getTimestamp("loginTime");

                User u = new User(id, userName, userPassword, userIdNumber, userPhone, enrollTime, loginTime);
                return u;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 用户录入
     *
     * @param u 要录入的对象
     * @return 录入的结果
     */
    @Override
    public boolean insert(User u) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数(可选)
            state.setString(1,u.getUserName());
            state.setString(2,u.getUserPhone());
            state.setString(3,u.getUserIdNumber());
            state.setString(4,u.getUserPassword());
            //4.    执行SQL语句(插入操作的返回值：成功：1；失败：0。)
            //5.    获取执行的结果
            return (state.executeUpdate() > 0);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 用户的修改
     *
     * @param id      要修改用户的id
     * @param newUser 新的用户对象
     * @return 修改的结果
     */
    @Override
    public boolean update(int id, User newUser) {
        PreparedStatement state = null;
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        //DruidUtil.getConnection()+alt+enter
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_UPDATE);
            //3.    填充参数(可选)
            //UPDATE USER SET USERNAME=?,USERPHONE=?,USERIDNUMBER=?,USERPASSWORD=? WHERE ID=?
            state.setString(1, newUser.getUserName());
            state.setString(2,newUser.getUserPhone());
            state.setString(3,newUser.getUserIdNumber());
            state.setString(4,newUser.getUserPassword());
            state.setInt(5,id);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return (state.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 更新登录时间
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateLoginTime(int id) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.    填充参数(可选)
            //UPDATE USER SET LOGINTIME=NOW() WHERE ID=?
            state.setInt(1,id);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return (state.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id，删除单个用户信息
     *
     * @param id 要删除的用户信息
     * @return 删除结果
     */
    @Override
    public boolean delete(int id) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            //2.    预编译SQL语句
            state = conn.prepareStatement(SQL_DELETE);
            //3.    填充参数(可选)
            //DELETE FROM USER WHERE ID=?
            state.setInt(1,id);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return (state.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
