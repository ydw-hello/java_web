package com.ydw.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ydw.dao.FruitDaoImpl;
import org.junit.Test;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ydw
 * @create 2022-07-10 23:21
 */
// 通过德鲁伊数据源获取数据库连接
public class DBUtils {
    private static DataSource dataSource = null;
    static {
        InputStream is = null;
        try {
            Properties properties = new Properties();
//            is = ClassLoader.getSystemResourceAsStream("db.properties");
            is = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("德鲁伊获取连接："+connection);
        return connection;
    }

    public static void closeResource(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(Connection connection, PreparedStatement preparedStatement){
        closeResource(connection);

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultset){
        closeResource(connection,preparedStatement);

        if (resultset != null) {
            try {
                resultset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws SQLException {
//        ClassLoader.getSystemResourceAsStream()
        Connection connection = DBUtils.getConnection();
        System.out.println(connection);
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        fruitDao.update(connection,
                "insert into fruit(name,price,count,remark) values(?,?,?,?)",
                "ydw",20,10,"你好");
        DBUtils.closeResource(connection);
    }
}
