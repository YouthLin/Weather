package com.youthlin.weather.test.sql;

import com.youthlin.weather.po.City;
import com.youthlin.weather.task.CityTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenml on 2016/7/24.
 * SQL Test
 */
public class SQLTest {
    private static final Logger log = LoggerFactory.getLogger(SQLTest.class);

    public static void main(String[] args) {
// java.sql.SQLException: The server time zone value '�й���׼ʱ��' is unrecognized or represents
// more than one time zone. You must configure either the server or JDBC driver
// (via the serverTimezone configuration property) to use a more specifc time zone value
// if you want to utilize time zone support.
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
    }
}
