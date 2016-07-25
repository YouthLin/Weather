package com.youthlin.weather.task;

import com.youthlin.weather.po.WeatherRecord;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenml on 2016/7/24.
 * 爬取提天气相关任务
 */
public class WeatherRecordTask {
    private static final Logger log = LoggerFactory.getLogger(WeatherRecordTask.class);

    private static final String HOST = "http://m.weathercn.com/";
    private static final String URL = "http://m.weathercn.com/index.do?id=";
    private static Map<String, String> cookies = null;

    public List<WeatherRecord> getWeatherRecordFromUrl(String id) throws IOException {
        List<WeatherRecord> records = new ArrayList<>(7);
        if (cookies == null) {
            cookies = Jsoup.connect(HOST).execute().cookies();
        }
        //m.weathercn.com/index.do?id=101010100&partner=
        Document doc = Jsoup.connect(URL + id)
                .cookies(cookies)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/51.0.2704.106 Safari/537.36")
                .get();
        Elements trs = doc.select("table.sevendays tr");
        Date today = new Date();
        String weather = null;
        WeatherRecord record;
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusDays(1);
        for (Element tr : trs) {
            dateTime = dateTime.plusDays(1);

            Elements tds = tr.children();
//            <tr>
//				<td class="date">星期天(07/24)</td>
//				<td class="icon-day">
//				</td>
//				<td class="icon-night">
//				 <img src="/images/small/night/Night10.png">
//				</td>
//				<td class="temp">
//                    24℃
//				</td>
//				<td class="desc">
//                    暴雨
//                    </td>
//			</tr>
            record = new WeatherRecord();
            record.setDay(dateTime.toDate());

            Element img = tds.get(1);
            if (img.children().size() > 0) {
                img = img.child(0);
                weather = img.attr("src");
            }
            if (weather != null) weather = weather.trim();
            if (weather != null && weather.length() > 0) {
                //<img src="/images/small/day/Day08.png"/>
                weather = weather.substring(weather.indexOf("/Day") + 4, weather.indexOf(".png"));
                record.setWeatherDay(Integer.parseInt(weather));
            }
//            log.debug("text={}", weather);

            img = tds.get(2);
            if (img.children().size() > 0) {
                img = img.child(0);
                weather = img.attr("src");
            }
            if (weather != null) weather = weather.trim();
            if (weather != null && weather.length() > 0) {
                //<img src="/images/small/night/Night10.png">
                weather = weather.substring(weather.indexOf("/Night") + 6, weather.indexOf(".png"));
                record.setWeatherNight(Integer.parseInt(weather));
            }
//            log.debug("text={}", weather);

            record.setTemperature(tds.get(3).text().trim());
            record.setDescription(tds.get(4).text().trim());
            record.setLastUpdate(today);

            log.debug("获取一条天气记录{}", record);
            records.add(record);
        }
        return records;
    }


    public static void main(String[] args) throws IOException {
        WeatherRecordTask task = new WeatherRecordTask();
        task.getWeatherRecordFromUrl("101020100");
    }
}
