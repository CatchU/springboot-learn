package com.catchu.proxys;

import com.catchu.builders.XDBSqlSessionFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class XDBProxy<T> implements InvocationHandler {

    private SqlSession sqlSession;
    private T target;
    private Boolean autoCommit;

    public XDBProxy(int dbNumber, Class<T> clazz) {
        this(dbNumber, clazz, true);
    }

    public XDBProxy(int dbNumber, Class<T> clazz, Boolean autoCommit) {
        XDBSqlSessionFactoryBuilder builder = XDBSqlSessionFactoryBuilder.getInstance();
        SqlSessionFactory sessionFactory = builder.getXDBAbsSqlsessionFactoryList().get(dbNumber).getSqlsessionFactoryW();
        this.autoCommit = ((null == autoCommit) ? true : autoCommit);
        this.sqlSession = sessionFactory.openSession(autoCommit);
        this.target = sqlSession.getMapper(clazz);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Object result = null;
        try {
            result = method.invoke(target, args);
        } finally {
            if (autoCommit) {
                closeResource();
            }
        }
        return result;
    }

    public void closeResource() {
        sqlSession.close();
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public static <T> XDBProxy<T> getInstance(int dbNumber, Class<?> clazz) {
        return getInstance(dbNumber, clazz, true);
    }

    public static <T> XDBProxy<T> getInstance(int dbNumber, Class<?> clazz, Boolean autoCommit) {
        return new XDBProxy(dbNumber, clazz, autoCommit);
    }
}
