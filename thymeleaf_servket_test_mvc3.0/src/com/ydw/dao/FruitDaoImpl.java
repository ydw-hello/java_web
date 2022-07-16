package com.ydw.dao;

import com.ydw.bean.Fruit;
import com.ydw.utils.DBUtils;

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

    @Override
    public Fruit getFruitById(Integer id) {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            Fruit fruit = query(connection, "select id,name,price,count,remark from fruit where id=?", id);
            return fruit;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeResource(connection);
        }
        return null;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        Connection connection = null;
        try{
            connection = DBUtils.getConnection();
            Object[] args = {fruit.getName(),fruit.getPrice(),fruit.getCount(),fruit.getRemark(),fruit.getId()};
            update(connection,"update fruit set name=?,price=?,count=?,remark=? where id=?",args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeResource(connection);
        }
    }

    @Override
    public void delFruitById(int id) {
        Connection connection = null;
        try{
            connection = DBUtils.getConnection();

            update(connection,"delete from fruit where id=?",id);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeResource(connection);
        }
    }

    @Override
    public List<Fruit> getPageFruitList(int pageNumber) {
        List<Fruit> fruits = null;
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //1 0-2;1 3-5
            int size = 3;
            int start = (pageNumber-1)*size;
            fruits = queryList(conn, "select id,name,price,count,remark from fruit limit ?,?",start,size);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResource(conn);
        }
        return fruits;
    }

    @Override
    public List<Fruit> getPageFruitList(int pageNumber, String keyword) {
        List<Fruit> fruits = null;
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //1 0-2;1 3-5
            int size = 3;
            int start = (pageNumber-1)*size;
            String key = "%"+keyword+"%";
            fruits = queryList(conn, "select id,name,price,count,remark from fruit where name like ? or remark like ? limit ?,?",key,key,start,size);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResource(conn);
        }
        return fruits;
    }

    @Override
    public Long getTotalCount() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Long value = getValue(conn, "select count(*) from fruit");
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResource(conn);
        }
        return 0L;
    }

    @Override
    public Long getTotalCount(String keyword) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String key = "%"+keyword+"%";
            Long value = getValue(conn, "select count(*) from fruit where name like ? or remark like ?",key,key);
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResource(conn);
        }
        return 0L;
    }
}
