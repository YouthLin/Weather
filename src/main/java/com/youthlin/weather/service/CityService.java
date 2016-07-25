package com.youthlin.weather.service;

import com.youthlin.weather.po.City;

import java.util.List;
import java.util.Set;

/**
 * Created by chenml on 2016/7/23.
 * City Service
 */
public interface CityService {
    boolean checkDB();

    int addCity();

    List<City> search(String queryStr);

    City getCity(String id);

    List<City> getHotCities();

}
