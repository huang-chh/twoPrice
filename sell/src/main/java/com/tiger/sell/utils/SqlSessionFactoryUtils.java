package com.tiger.sell.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {

//    //线程锁
//    private final static Class<SqlSessionFactoryUtils> LOCK = SqlSessionFactoryUtils.class;
//    //无参的构造器
//    public SqlSessionFactoryUtils() {
//    }
//    private static SqlSessionFactory sqlSessionFactory = null;
//    public static SqlSessionFactory getSqlSessionFactory(){
//            synchronized (LOCK){
//                if(sqlSessionFactory!=null){
//                    return sqlSessionFactory;
//                }
//                try {
//
//                    InputStream inputStream = Resources.getResourceAsStream("mybatsi-config.xml");
//                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//                } catch (IOException e) {
//                    return null;
//                }
//                return sqlSessionFactory;
//            }
//
//    }




}
