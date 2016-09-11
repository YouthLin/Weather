package com.youthlin.weather.dao.impl;

import com.youthlin.weather.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenml on 2016/7/23.
 * 实现类
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T get(Class<T> entityClazz, Serializable id) {
        return getSessionFactory().getCurrentSession().get(entityClazz, id);
    }

    @Override
    public Serializable save(T entity) {
        log.debug("update {}", entity);
        return getSessionFactory().getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        log.debug("update {}", entity);
        getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void refresh(T entity) {
        log.debug("refresh {}", entity);
        getSessionFactory().openSession().refresh(entity);
    }

    @Override
    public void merge(T entity) {
        log.debug("merge {}", entity);
        getSessionFactory().openSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getSessionFactory().getCurrentSession().delete(entity);
    }

    @Override
    public void delete(Class<T> entityClazz, Serializable id) {
        getSessionFactory().getCurrentSession()
                .createQuery("delete " + entityClazz.getSimpleName() + " en where en.id = ?1")
                .setParameter("1", id).executeUpdate();
    }

    @Override
    public void flush() {
        getSessionFactory().getCurrentSession().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> find(String hql) {
        return (List<T>) getSessionFactory().getCurrentSession().createQuery(hql).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, Object... param) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0, len = param.length; i < len; i++) {
            query.setParameter(i, param[i]);
        }
        return (List<T>) query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, int start, int count) {
        return (List<T>) getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public List<T> findAll(Class<T> entityClass) {
        return find("select en from " + entityClass.getSimpleName() + " en");
    }

    @Override
    public long findCount(Class<T> entityClass) {
        List<?> list = find("select count(*) from " + entityClass.getSimpleName());
        if (list != null && list.size() == 1) {
            return (Long) list.get(0);
        }
        return 0;
    }
}
