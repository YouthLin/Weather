package com.youthlin.weather.dao.impl;

import com.youthlin.weather.dao.CityDao;
import com.youthlin.weather.dao.WeatherRecordDao;
import com.youthlin.weather.po.City;
import com.youthlin.weather.po.WeatherRecord;
import com.youthlin.weather.task.WeatherRecordTask;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenml on 2016/7/24.
 * 天气DAO实现类
 */
public class WeatherRecordDaoImpl extends BaseDaoImpl<WeatherRecord> implements WeatherRecordDao {

    private CityDao cityDao;

    @Override
    public boolean update(String cityId) {
        City city = cityDao.get(City.class, cityId);
        WeatherRecordTask task = new WeatherRecordTask();
        try {
            log.debug("爬取最新天气预报信息");
            //获取最新预报数据
            List<WeatherRecord> records = task.getWeatherRecordFromUrl(cityId);
            //数据库中的旧记录
            List<WeatherRecord> dbRecords = city.getWeatherRecords();
            for (WeatherRecord wr : dbRecords) {
                wr.setCity(null);           //需要先设置city为空，否则会把city级联删除的！
                delete(wr);                 //删除旧记录
            }
            dbRecords.clear();
            dbRecords.addAll(records);      //设置最新爬取的记录
            update(records, city);
//            cityDao.update(city);
////            cityDao.merge(city);
////            cityDao.refresh(city);
            cityDao.flush();                //把代码中的更改同步到数据库中
        } catch (IOException e) {
            log.warn("Exception when get weather record from URL {}", e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取七天天气预报
     * 1.数据库无记录，爬取数据
     * 2.数据库有记录，但不是今天的最新数据，爬取数据更新
     * 3.数据库有记录，且最后更新时间是今天，直接取出
     */
    @Override
    public List<WeatherRecord> gotSevenDaysWeatherRecords(String cityId) {
        DateTime today = new DateTime();
        City city = cityDao.get(City.class, cityId);
        if (city == null) {
            return new ArrayList<>(0);
        }
        city.setSearchCount(city.getSearchCount() + 1);
        cityDao.update(city);
        flush();

        List<WeatherRecord> list = city.getWeatherRecords();

        log.debug("records={}", list);
        int count = list.size();
        if (count > 6) {
            //有记录,看更新时间是否为今天
            DateTime lastUpdate = new DateTime(list.get(count - 1).getLastUpdate());
            int minus = today.getDayOfMonth() - lastUpdate.getDayOfMonth();
            if (minus == 0 || (minus == 1 && today.getHourOfDay() < 8)) {
                //更新时间是今天,或是昨天但今天还没有新的数据
                log.debug("数据库中直接获取天气预报");
                return list;
            }
        }
        update(cityId);
        return gotSevenDaysWeatherRecords(cityId);
    }

    @Override
    public void update(List<WeatherRecord> records, City city) {
        log.debug("update records");
        for (WeatherRecord record : records) {
            record.setCity(city);
//            save(record);
        }
    }

    public City getCity(String id) {
        return cityDao.get(City.class, id);
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }
}
