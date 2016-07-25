package com.youthlin.weather.po;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenml on 2016/7/24.
 * 天气信息
 */
@Entity
public class WeatherRecord {
    @Id
    private Date day;
    private int weatherDay;
    private int weatherNight;
    private String temperature;
    private String description;
    private Date lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private City city;

    public Date getDay() {
        return day;
    }

    public void setDay(Date date) {
        this.day = date;
    }

    public int getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(int weatherDay) {
        this.weatherDay = weatherDay;
    }

    public int getWeatherNight() {
        return weatherNight;
    }

    public void setWeatherNight(int weatherNight) {
        this.weatherNight = weatherNight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temp) {
        this.temperature = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherRecord{" +
                "date=" + day +
                ", weatherDay=" + weatherDay +
                ", weatherNight=" + weatherNight +
                ", temp='" + temperature + '\'' +
                ", desc='" + description + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public String getTime() {
        return  new DateTime(getDay()).toString("MM-dd (EE)");
    }

    public String getDayIconNum() {
        return String.format("%02d", getWeatherDay());
    }

    public String getNightIconNum() {
        return String.format("%02d", getWeatherNight());
    }

}
