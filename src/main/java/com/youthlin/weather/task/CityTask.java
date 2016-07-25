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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chenml on 2016/7/22.
 * 获取城市列表相关任务
 * Jsoup 用法： http://www.open-open.com/jsoup/
 */
public class CityTask {
    private static final Logger LOG = LoggerFactory.getLogger(CityTask.class);
    private static final String ALL_PROVINCES_URL = "http://m.weathercn.com/citychange.jsp?partner=m";
    private static final String HOST = "http://m.weathercn.com/";

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

                    LOG.trace("add city: " + city.toString());
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
        LOG.debug("get cities in {}", provinceName);
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
            if (href.contains("stationlist.do")) {
                String stationName = href.substring(href.indexOf("cityen=") + 7, href.lastIndexOf("&"));
                cities.addAll(getCitiesInStation(href, text + stationName, text));
            } else if (href.contains("tocitylist.do")) {
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
        List<City> cities = new ArrayList<>(2645);
        InputStream inputStream = getClass().getResourceAsStream("/cities.txt");
        Scanner in = new Scanner(inputStream);
        while (in.hasNext()) {
//            System.out.println(in.nextLine());
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

        LOG.trace("parse city {}", city);

        return city;
    }

    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        List<City> cityList = new CityTask().getAllCitesFromFile();
//        Set<String> ids = new HashSet<>(2700);
        for (City city : cityList) {
            session.save(city);
//            if (ids.contains(city.getCityId())) {
//                log.warn("重复ID{}", city);
//            }
//            ids.add(city.getCityId());
        }

        tx.commit();
        session.close();
        sf.close();


//        LOG.trace("trace log");
//
////        new CityTask().getAllCitesFromFile();
//        String hainan = "tocitylist.do?pid=10131&partner=";
//        try {
//
////            String xinjiang = "tocitylist.do?pid=10113&partner=";
////            String hk = "tocitylist.do?pid=10132&partner=";
////            String maucao = "tocitylist.do?pid=10133&partner=";
////            String tw = "tocitylist.do?pid=10134&partner=";
//            CityTask cityTask = new CityTask();
////            cityTask.getAllCites();
//            List<City> cities = cityTask.getCitiesInCity(hainan, "海南");
////            List<City> cities = cityTask.getCitiesInCity(xinjiang, "新疆");
////            cities.addAll(cityTask.getCitiesInCity(hk, "香港"));
////            cities.addAll(cityTask.getCitiesInCity(maucao, "澳门"));
////            cities.addAll(cityTask.getCitiesInCity(tw, "台湾"));
//            FileWriter out = new FileWriter(new File("D:/cities-hainan.txt"));
//            for (City city : cities) {
//                System.out.println(city);
//                out.write(city + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
