package com.youthlin.weather.task;

import com.youthlin.weather.po.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by chenml on 2016/7/22.
 * 获取城市列表相关任务
 * Jsoup 用法： http://www.open-open.com/jsoup/
 */
public class CityTask {
    private static final String ALL_PROVINCES_URL = "http://m.weathercn.com/citychange.jsp?partner=m";
    private static final String HOST = "http://m.weathercn.com/";
    private static final Logger log = LoggerFactory.getLogger(CityTask.class);

    /**
     * 获取一个市的所有城市
     *
     * @param url 类似于 stationlist.do?pid=10109&cityen=baoding&partner= 的各县市区超链接
     * @return 市里的所有最小的县区城市单位
     */
    private List<City> getCitiesInStation(String url, String stationName, String provinceName) throws IOException {
        List<City> cities = new ArrayList<>();
        Document doc = Jsoup.connect(HOST + url).get();
        Elements uls = doc.select("div.main-body div.city-list ul");
        Elements lis;
        String name;
        String id;
        for (Element ul : uls) {
            lis = ul.children();
            for (Element li : lis) {
                name = li.attr("data-index");
                if (name.length() > 0) {
                    Element a = li.child(0);
                    String href = a.attr("href");
                    id = href.substring(href.indexOf("=") + 1, href.indexOf("&"));

                    City city = new City();
                    city.setCityId(id);
                    city.setCityName(name);
                    city.setStationName(stationName);
                    city.setProvinceName(provinceName);
                    cities.add(city);

                    log.trace("add city: " + city.toString());
                }

            }
        }
        return cities;
    }

    /**
     * 获取一个省的所有城市
     *
     * @param url 类似于 tocitylist.do?pid=10109&partner=m 的各省超链接
     * @return 省份里的所有城市
     */
    private List<City> getCitiesInCity(String url, String provinceName) throws IOException {
        log.debug("get cities in {}", provinceName);
        List<City> cities = new ArrayList<>();
        Document doc = Jsoup.connect(HOST + url).get();
        Elements uls = doc.select("div.main-body div.city-list ul");
        String stationName;
        for (Element ul : uls) {
            Elements lis = ul.children();
            for (Element li : lis) {
                stationName = li.attr("data-index");
                if (stationName.length() > 0) {
                    Element a = li.child(0);
                    cities.addAll(getCitiesInStation(a.attr("href"), stationName, provinceName));
                }
            }
        }
        return cities;
    }

    /**
     * 获取所有的城市
     *
     * @return 城市列表
     */
    public List<City> getAllCites() throws IOException {
        List<City> cities = new ArrayList<>();

        Document doc = Jsoup.connect(ALL_PROVINCES_URL).get();
        Elements provinces = doc.select("div.selfcity dd a");
        String href, text;
        for (Element province : provinces) {
            //System.out.println("href=" + province.attr("href") + ",text=" + province.text());
            href = province.attr("href");
            text = province.text();
            if (href.contains("stationlist.do")) {//直辖市下的区
                String stationName = href.substring(href.indexOf("cityen=") + 7, href.lastIndexOf("&"));
                cities.addAll(getCitiesInStation(href, text + stationName, text));
            } else if (href.contains("tocitylist.do")) {//市
                cities.addAll(getCitiesInCity(href, text));
            }
        }
//            FileWriter out = new FileWriter(new File("D:/cities.txt"));
//            for (City city : cities) {
//                System.out.println(city);
//                out.write(city + "\n");
//            }
        return cities;
    }

    public List<City> getAllCitesFromFile() {
        List<City> cities = new ArrayList<>(2650);
        InputStream inputStream = getClass().getResourceAsStream("/cities.txt");
        //设置编码！当直接运行本文件的main方法时，是正常的UTF-8，当通过web访问Controller调用时就成了GBK，因此在这里设置。
        Scanner in = new Scanner(inputStream, "UTF-8");
        while (in.hasNext()) {
            City city = parseCity(in.nextLine());
            cities.add(city);
        }
        return cities;
    }

    private City parseCity(String str) {
        City city = new City();
        String cityId = str.substring(str.indexOf("cityId='") + 8, str.indexOf("', cityName"));
        String cityName = str.substring(str.indexOf("cityName='") + 10, str.indexOf("', stationName"));
        String stationName = str.substring(str.indexOf("stationName='") + 13, str.indexOf("', provinceName"));
        String provinceName = str.substring(str.indexOf("provinceName='") + 14, str.lastIndexOf("'}"));
        city.setCityId(cityId);
        city.setCityName(cityName);
        city.setStationName(stationName);
        city.setProvinceName(provinceName);

        log.trace("parse city {}", city);

        return city;
    }

    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        log.trace("读取资源文件");
        List<City> cityList = new CityTask().getAllCitesFromFile();
        log.debug("第一条数据为{}", cityList.get(0));
        for (City city : cityList) {
            session.save(city);
        }
        log.trace("保存数据成功");

        tx.commit();
        session.close();
        sf.close();
    }
}
