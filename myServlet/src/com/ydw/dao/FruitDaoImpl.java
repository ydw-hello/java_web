package com.ydw.dao;

import com.ydw.bean.Fruit;
import com.ydw.utils.DBUtils;

import java.lang.annotation.Repeatable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-11 0:19
 */
public class FruitDaoImpl extends BaseDao<Fruit> implements FruitDao {
    @Override
    public void add(Fruit fruit) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "insert into fruit(name,price,count,remark) values(?,?,?,?)";
            update(conn, sql, fruit.getName(), fruit.getPrice(), fruit.getCount(), fruit.getRemark());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DBUtils.closeResource(conn);
        }

    }

    @Override
    public List<Fruit> getFruitList() {
        List<Fruit> fruits = null;
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            fruits = queryList(conn, "select id,name,price,count,remark from fruit");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResource(conn);
        }
        return fruits;
    }
}
