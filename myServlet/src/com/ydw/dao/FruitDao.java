package com.ydw.dao;

import com.ydw.bean.Fruit;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-10 23:16
 */
public interface FruitDao {
    void add(Fruit fruit);

    List<Fruit> getFruitList() throws SQLException;
}
