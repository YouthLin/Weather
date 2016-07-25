package com.youthlin.weather.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenml on 2016/7/23.
 * BaseDao 接口
 */
public interface BaseDao<T> {
    T get(Class<T> entityClazz, Serializable id);

    Serializable save(T entity);

    void update(T entity);

    void refresh(T entity);

    void merge(T entity);

    void delete(T entity);

    void delete(Class<T> entityClazz, Serializable id);

    void flush();

    List<T> findAll(Class<T> entityClass);

    long findCount(Class<T> entityClass);

    List<T> find(String hql);

    List<T> find(String hql, Object... param);

    List<T> find(String hql, int start, int count);

    Session getSession();
}
