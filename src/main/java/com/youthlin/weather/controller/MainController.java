package com.youthlin.weather.controller;

import com.youthlin.weather.po.City;
import com.youthlin.weather.service.CityService;
import com.youthlin.weather.service.WeatherRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by chenml on 2016/7/21.
 * 控制器
 */
@Controller
public class MainController {
    @Autowired
    private CityService cityService;
    @Autowired
    private WeatherRecordService weatherRecordService;

    /**
     * 输入提示
     */
    @RequestMapping(value = {"/search", "/search.html"}, method = RequestMethod.GET)
    public void search(@RequestParam("cityName") String name, ServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<City> cities = cityService.search(name);
        StringBuilder sb = new StringBuilder("[");
        for (City city : cities) {
            sb.append(city.toJsonString()).append(",");
        }
        if (cities.size() > 0) sb.deleteCharAt(sb.length() - 1);//去掉最后一个逗号
        sb.append("]");
        out.println(sb.toString());
    }

    @RequestMapping(value = {"/", "/index", "/index.html"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("city", new City());//对应页面的form表单
        model.addAttribute("hotCities", cityService.getHotCities());
        return "app.index";
    }

    @RequestMapping(value = {"/weather", "/weather.html"}, method = RequestMethod.GET)
    public String weather(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id == null) id = "101010100";
        model.addAttribute("city", cityService.getCity(id));
        model.addAttribute("records", weatherRecordService.gotWeatherRecords(id));
        return "app.weather";
    }

    @RequestMapping(value = {"/about", "/about.html"}, method = RequestMethod.GET)
    public String about() {
        return "app.about";
    }

}
