package com.mastiff.test;

import com.mastiff.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MybatisTest {

    //快速入门测试方法
    @Test
    public void mybatisQuickStart() throws IOException {

        //1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        //2.获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.执行sql
        List<User> users = sqlSession.selectList("userMapper.findAll");

        //5.遍历打印结果
        for (User user:users) {
            System.out.println(user);
        }

        //6.关闭资源
        sqlSession.close();
    }

    //测试新增用户
    @Test
    public void testSave() throws IOException {

        //1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        //2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //4.准备User对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d  = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d  = sdf.parse("2017-11-06 0:0:0");
        }catch (Exception e){
            e.printStackTrace();
        }
        User user1 = new User();
        user1.setUsername("mastiff");
        user1.setAddress("beijing");
        user1.setBirthday(d);
        user1.setSex("女");
        //5.执行sql
        int row = sqlSession.insert("userMapper.saveUser", user1);
        if (1 == row) {
            System.out.println("新增用户成功");
            //手动提交事务
//            sqlSession.commit();
        } else {
            System.out.println("新增用户失败");
        }

        //6.关闭资源
        sqlSession.close();
    }

    //测试修改用户
    @Test
    public void testUpdate() throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d  = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d  = sdf.parse("2017-11-06 0:0:0");
        }catch (Exception e){
            e.printStackTrace();
        }
        User user1 = new User();
        user1.setUsername("木叶下");
        user1.setAddress("beijing");
        user1.setBirthday(d);
        user1.setSex("女");
        user1.setId(7);
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int row = sqlSession.update("userMapper.updateUser", user1);
        if (1 == row) {
            System.out.println("修改用户信息成功");
            sqlSession.commit();
        } else {
            System.out.println("修改用户信息失败");
        }
        sqlSession.close();
    }

    //测试删除用户
    @Test
    public void testDelete() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int row = sqlSession.delete("userMapper.deleteUser", 9);
        if (1 == row) {
            System.out.println("删除用户成功");
            sqlSession.commit();
        } else {
            System.out.println("删除用户失败");
        }
        sqlSession.close();
    }
}
