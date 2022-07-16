package com.ydw.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-11 0:00
 */
public class BaseDao<T> {

    private QueryRunner queryRunner = new QueryRunner();
    private Class<T> clazz;

    public BaseDao(){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }
    // 通用的增删改方法
    public int update(Connection connection,String sql,Object ...args) throws SQLException {
        return queryRunner.update(connection,sql,args);
    }

    // 通用的查询方法
    public T query(Connection connection,String sql,Object ...args) throws SQLException {
        BeanHandler<T> handler = new BeanHandler<T>(clazz);
        return queryRunner.query(connection,sql,handler,args);
    }

    public List<T> queryList(Connection connection,String sql,Object ...args) throws SQLException {
        BeanListHandler<T> listHandler = new BeanListHandler<T>(clazz);
        List<T> query = queryRunner.query(connection, sql, listHandler, args);
        return query;
    }

    // 查询特定列的方法
    public <E> E getValue(Connection connection,String sql,Object...args) throws SQLException {
        ScalarHandler<E> eScalarHandler = new ScalarHandler<>();
        return (E) query(connection,sql,eScalarHandler,args);
    }
}
