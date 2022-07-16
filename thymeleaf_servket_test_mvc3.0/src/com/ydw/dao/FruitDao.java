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

    List<Fruit> getFruitList() ;

    Fruit getFruitById(Integer id);

    void updateFruit(Fruit fruit);

    void delFruitById(int id);

    // 获取指定页码上的数据，每页显示5条;pageNumber,从0开始
    List<Fruit> getPageFruitList(int pageNumber);
    // 按条件，获取指定页码上的数据，每页显示5条;pageNumber,从0开始
    List<Fruit> getPageFruitList(int pageNumber,String keyword);


     // 获取总条数
    Long getTotalCount();

    // 根据条件获取总条数
    Long getTotalCount(String keyword);


}
