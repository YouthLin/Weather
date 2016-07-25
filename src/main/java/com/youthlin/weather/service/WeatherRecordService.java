package com.youthlin.weather.service;

import com.youthlin.weather.po.WeatherRecord;

import java.util.List;

/**
 * Created by chenml on 2016/7/24.
 * 天气相关服务
 */
public interface WeatherRecordService {
    List<WeatherRecord> gotWeatherRecords(String cityId);
}
