package com.youthlin.weather.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenml on 2016/7/22.
 * 城市
 */
@Entity
public class City {
    @Id
    private String cityId;    /*有的外国城市Id是包含字母的*/
    private String cityName;
    private String stationName;
    private String provinceName;
    private long searchCount;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WeatherRecord> weatherRecords = new ArrayList<>();

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<WeatherRecord> getWeatherRecords() {
        return weatherRecords;
    }

    public void setWeatherRecords(List<WeatherRecord> weatherRecords) {
        this.weatherRecords = weatherRecords;
    }

    public long getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(long searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", searchCount=" + searchCount +
                ", weatherRecords=" + weatherRecords +
                '}';
    }

    public String toJsonString() {
        return "{\"cityId\":\"" + cityId
                + "\",\"cityName\":\"" + removeEnWords(cityName)
                + "\",\"stationName\":\"" + removeEnWords(stationName)
                + "\",\"provinceName\":\"" + removeEnWords(provinceName) + "\"}";
    }

    private String removeEnWords(String word) {
        return word.replaceAll("[a-zA-Z]", "");
    }

    public String getCityNameCN() {
        return getCityName().replaceAll("[a-zA-Z]", "");
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        City city = (City) o;
//
//        if (cityId != null ? !cityId.equals(city.cityId) : city.cityId != null) return false;
//        if (cityName != null ? !cityName.equals(city.cityName) : city.cityName != null) return false;
//        if (stationName != null ? !stationName.equals(city.stationName) : city.stationName != null) return false;
//        return provinceName != null ? provinceName.equals(city.provinceName) : city.provinceName == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = cityId != null ? cityId.hashCode() : 0;
//        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
//        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
//        result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
//        return result;
//    }
}
