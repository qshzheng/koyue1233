package com.example.koyue123;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBUtils {
    private static final String TAG = "DBUtils";
    private static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            //System.out.println("数据库加载成功");
            String ip = "192.168.1.19";
            String user = "root";
            String password = "zxm_337105368zxm";
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName + "",
                    user, password);
            System.out.println(conn);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static HashMap<String, String> getUserInfoByName(String name) {
        HashMap<String, String> map = new HashMap<>();
        Connection conn = getConnection("koyue");
        try {
            Statement st = conn.createStatement();
            String sql = "select * from account where name = '" + name + "'";
            ResultSet res = st.executeQuery(sql);
            if (res == null) {
                return null;
            } else {
                int cnt = res.getMetaData().getColumnCount();
                //res.last(); int rowCnt = res.getRow(); res.first();
                res.next();
                for (int i = 1; i <= cnt; ++i) {
                    String field = res.getMetaData().getColumnName(i);
                    map.put(field, res.getString(field));
                }
                conn.close();
                st.close();
                res.close();
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }
}

