package com.youthlin.weather.dao.impl;

import com.youthlin.weather.dao.CityDao;
import com.youthlin.weather.dao.WeatherRecordDao;
import com.youthlin.weather.po.City;
import com.youthlin.weather.po.WeatherRecord;
import com.youthlin.weather.task.WeatherRecordTask;
import org.joda.time.DateTime;

import java.io.IOException;
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
            //插入或更新
            city.getWeatherRecords().addAll(records);
            update(records, city);
//            cityDao.update(city);
////            cityDao.merge(city);
////            cityDao.refresh(city);
            cityDao.flush();
        } catch (IOException e) {
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

        city.setSearchCount(city.getSearchCount() + 1);
        cityDao.update(city);
        flush();

        List<WeatherRecord> list = city.getWeatherRecords();
        //org.hibernate.hql.internal.ast.QuerySyntaxException: WeatherRecord is not mapped
        // = find("from " + WeatherRecord.class.getSimpleName()
        //                        + " record where record.cityId = ? and  record.day >= ? and record.day <= ?",
        //                cityId, today, today.plusDays(7));
        log.debug("records={}", list);
        int count = list.size();
        if (count > 6) {
            //有记录,看更新时间是否为今天
            DateTime lastUpdate = new DateTime(list.get(count - 1).getLastUpdate());
            if (lastUpdate.getDayOfMonth() == today.getDayOfMonth()) {
                //更新时间是今天
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
