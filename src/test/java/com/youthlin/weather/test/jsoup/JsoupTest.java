package com.youthlin.weather.test.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by chenml on 2016/7/22.
 * Jsoup Test
 */
public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://m.weathercn.com/citychange.jsp?partner=m").get();
        /*
        <dd class="clearfix">
                <a href="stationlist.do?pid=10101&cityen=beijing&partner=m">北京</a>
				<a href="stationlist.do?pid=10103&cityen=tianjin&partner=m">天津</a>
				<a href="stationlist.do?pid=10104&cityen=chongqing&partner=m">重庆</a>
				<a href="stationlist.do?pid=10102&cityen=shanghai&partner=m">上海</a>
				<a href="tocitylist.do?pid=10109&partner=m">河北</a>
				<a href="tocitylist.do?pid=10110&partner=m">山西</a>
				<a href="tocitylist.do?pid=10107&partner=m">辽宁</a>
				<a href="tocitylist.do?pid=10106&partner=m">吉林</a>
				<a href="tocitylist.do?pid=10105&partner=m">黑龙江</a>
				<a href="tocitylist.do?pid=10119&partner=m">江苏</a>
				<a href="tocitylist.do?pid=10121&partner=m">浙江</a>
				<a href="tocitylist.do?pid=10122&partner=m">安徽</a>
				<a href="tocitylist.do?pid=10123&partner=m">福建</a>
				<a href="tocitylist.do?pid=10124&partner=m">江西</a>
				<a href="tocitylist.do?pid=10112&partner=m">山东</a>
				<a href="tocitylist.do?pid=10118&partner=m">河南</a>
				<a href="tocitylist.do?pid=10120&partner=m">湖北</a>
				<a href="tocitylist.do?pid=10125&partner=m">湖南</a>
				<a href="tocitylist.do?pid=10128&partner=m">广东</a>
				<a href="tocitylist.do?pid=10125&partner=m">海南</a>
				<a href="tocitylist.do?pid=10127&partner=m">四川</a>
				<a href="tocitylist.do?pid=10126&partner=m">贵州</a>
				<a href="tocitylist.do?pid=10129&partner=m">云南</a>
				<a href="tocitylist.do?pid=10111&partner=m">陕西</a>
				<a href="tocitylist.do?pid=10116&partner=m">甘肃</a>
				<a href="tocitylist.do?pid=10115&partner=m">青海</a>
				<a href="tocitylist.do?pid=10108&partner=m">内蒙古</a>
				<a href="tocitylist.do?pid=10130&partner=m">广西</a>
				<a href="tocitylist.do?pid=10114&partner=m">西藏</a>
				<a href="tocitylist.do?pid=10117&partner=m">宁夏</a>
				<a href="tocitylist.do?pid=10113&partner=m">新疆</a>
				<a href="tocitylist.do?pid=10132&partner=m">香港</a>
				<a href="tocitylist.do?pid=10133&partner=m">澳门</a>
				<a href="tocitylist.do?pid=10134&partner=m">台湾</a>
			</dd>
*/
        Elements citys = doc.select("div.selfcity dd a");
        for (Element city : citys) {
            String href = city.attr("href");
            System.out.println(city.text() + href.substring(href.indexOf("cityen=") + 7, href.lastIndexOf("&")));
            //System.out.println("href=" + city.attr("href") + ",text=" + city.text());
        }

        doc = Jsoup.connect("http://m.weathercn.com/stationlist.do?pid=10101&cityen=beijing&partner=m").get();
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
                    //index.do?id=101010200&partner=
                    id = href.substring(href.indexOf("=") + 1, href.indexOf("&"));
                    System.out.println(name + "," + id);
                }

            }
        }
    }
}
