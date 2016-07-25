package com.youthlin.weather.service.impl;

import com.youthlin.weather.dao.CityDao;
import com.youthlin.weather.po.City;
import com.youthlin.weather.service.CityService;
import com.youthlin.weather.task.CityTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenml on 2016/7/23.
 * 实现类
 */
public class CityServiceImpl implements CityService {
    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
    private CityDao cityDao;
    private static List<City> cities = null;

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public boolean checkDB() {
        return cityDao.checkData();
    }

    @Override
    public int addCity() {
        CityTask cityTask = new CityTask();
        int count = 0;
        Set<String> citiesId = new HashSet<>(2700);
        if (cities == null) {
            cities = cityTask.getAllCitesFromFile();
        }
        log.debug("保存数据至数据库");
        for (City city : cities) {
//      有重复数据  A different object with the same identifier value was already associated with the session
            cityDao.save(city);
            if (citiesId.contains(city.getCityId())) {
                log.debug("重复{}", city);
            }
            citiesId.add(city.getCityId());
            count++;
        }
        log.debug("保存完成");
        return count;
    }

    @Override
    public List<City> search(String queryStr) {
        return cityDao.search(queryStr);
    }

    @Override
    public City getCity(String id) {
        return cityDao.get(City.class, id);
    }

    @Override
    public List<City> getHotCities() {
        return cityDao.getHotCities();
    }

}
