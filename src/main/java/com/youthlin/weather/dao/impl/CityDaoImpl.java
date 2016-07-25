package com.youthlin.weather.dao.impl;

import com.youthlin.weather.dao.CityDao;
import com.youthlin.weather.po.City;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenml on 2016/7/23.
 * 实现类
 */
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao {

    @Override
    public boolean checkData() {
        long count = findCount(City.class);
        return count > 0;
    }

    @Override
    public List<City> getHotCities() {
        //desc 才是从多到少
        return find("from " + City.class.getSimpleName() + " city order by city.searchCount desc", 0, 12);
    }

    @Override
    public List<City> search(String queryStr) {
        return find("from " + City.class.getSimpleName() + " city where city.cityName like ?",
                '%' + queryStr + '%');
    }

}
