package com.youthlin.weather.dao;

import com.youthlin.weather.po.City;

import java.util.List;
import java.util.Set;

/**
 * Created by chenml on 2016/7/23.
 * Dao
 */
public interface CityDao extends BaseDao<City> {
    boolean checkData();

    List<City> getHotCities();

    List<City> search(String queryStr);
}
