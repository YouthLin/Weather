package com.youthlin.weather.service.impl;

import com.youthlin.weather.dao.WeatherRecordDao;
import com.youthlin.weather.po.WeatherRecord;
import com.youthlin.weather.service.WeatherRecordService;

import java.util.List;

/**
 * Created by chenml on 2016/7/24.
 * 天气服务实现类
 */
public class WeatherRecordServiceImpl implements WeatherRecordService {
    public WeatherRecordDao getWeatherRecordDao() {
        return weatherRecordDao;
    }

    public void setWeatherRecordDao(WeatherRecordDao weatherRecordDao) {
        this.weatherRecordDao = weatherRecordDao;
    }

    private WeatherRecordDao weatherRecordDao;

    @Override
    public List<WeatherRecord> gotWeatherRecords(String cityId) {
        return weatherRecordDao.gotSevenDaysWeatherRecords(cityId);
    }
}
