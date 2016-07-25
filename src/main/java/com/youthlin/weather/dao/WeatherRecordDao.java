package com.youthlin.weather.dao;

import com.youthlin.weather.po.City;
import com.youthlin.weather.po.WeatherRecord;

import java.util.List;

/**
 * Created by chenml on 2016/7/24.
 * 天气DAO
 */
public interface WeatherRecordDao extends BaseDao<WeatherRecord> {
    boolean update(String cityId);

    void update(List<WeatherRecord> records,City city);

    List<WeatherRecord> gotSevenDaysWeatherRecords(String cityId);
}
