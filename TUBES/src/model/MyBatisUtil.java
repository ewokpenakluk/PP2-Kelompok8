package model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                // Mengambil file konfigurasi dari folder resource
                inputStream = Resources.getResourceAsStream("resource/mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException("Error MyBatis Config: " + e.getMessage());
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSession() {
        return getSqlSessionFactory().openSession(true); // true = autoCommit
    }
}