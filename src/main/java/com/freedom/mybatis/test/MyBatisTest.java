package com.freedom.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
 
import com.freedom.mybatis.domain.User;
import com.freedom.mybatis.mapper.UserMapper;
 
public class MyBatisTest {
	private static final Logger logger = LogManager.getLogger(MyBatisTest.class);
	
	private static final Object CLASS_LOCK = MyBatisTest.class;
	
	private static SqlSessionFactory sqlSessionFactory = null;
	
	public static SqlSessionFactory initSqlSessionFactory() {
		String config = "sqlMapConfig.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(config);
		} catch (IOException e) {
			logger.info("SqlSessionFactory:" + e.getMessage());
		}
		synchronized (CLASS_LOCK) {
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			}
		}
		
		return sqlSessionFactory;		
	}
	
	public static SqlSession openSession() {
		if (sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * @Title: getSqlSessionFactory
	 * @Description: 将增、删、改、查公共使用的部分提出来
	 * @param: @return
	 * @param: @throws
	 *             IOException
	 * @return: SqlSession
	 * @throws @author
	 *             BlackDragon
	 */
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String config = "sqlMapConfig.xml";
		// 1.加载核心配置文件
		InputStream inputStream = Resources.getResourceAsStream(config);
		// 2.读取配置文件内容
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
 
		return sqlSessionFactory;
	}
 
	/**
	 * @Title: addUser
	 * @Description: 新增用户
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void addUser() throws IOException {
		// 3.使用sqlSessionFactory对象，创建SqlSession对象，开启自动提交事务
		SqlSession sqlSession = this.getSqlSessionFactory().openSession(true);
		// 调用方法执行
		User user = new User();
		user.setId(2);
		user.setUsername("李诗诗");
		user.setBirthday(new Date());
		user.setSex("女");
		user.setAddress("来自大明朝");
		int ret = sqlSession.insert("mybatis.addUser", user);
		logger.info("sqlSession.insert() function returns:" + ret);
		// 事务提交
		sqlSession.commit();
		// 释放资源
		sqlSession.close();
	}
 
	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void addBatchUsers() throws IOException{
		SqlSession sqlSession = this.getSqlSessionFactory().openSession(true);
		List<User> userList = new ArrayList<User>();
		for(int i=0; i<10; i++) {
			User user = new User();
			user.setUsername("test"+i);
			user.setBirthday(new Date());
			user.setSex((i%2==0)?"男":"女");
			user.setAddress("address"+i);
			userList.add(user);
		}
		int ret = sqlSession.insert("mybatis.addBatchUsers", userList);
		logger.info("sqlSession.insert() function returns:" + ret);
		sqlSession.commit();
		sqlSession.close();
	}
	
	/**
	 * @Title: deleteUser
	 * @Description: 根据用户id删除用户
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void deleteUser() throws IOException {
		// 创建SqlSession对象
		SqlSession sqlSession = this.getSqlSessionFactory().openSession(true);
		// 调用方法执行
		sqlSession.delete("mybatis.deleteUser", 2);
		// 释放资源
		sqlSession.close();
	}
 
	/**
	 * @Title: updateUser
	 * @Description: 根据用户id修改用户
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void updateUserById() throws IOException {
		// 创建SqlSession对象
		SqlSession sqlSession = this.getSqlSessionFactory().openSession(true);
		// 调用方法执行
		// 创建用户对象
		User user = new User();
		user.setId(2);
		user.setUsername("zlj");
		user.setSex("男");
 
		sqlSession.update("mybatis.updateUserById", user);
		// 释放资源
		sqlSession.close();
	}
	
	@Test
	public void queryAllUsers() throws IOException {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		List<Object> userListOne = sqlSession.selectList("mybatis.queryAllUsers");
		logger.info("select first time");
		for (Object object : userListOne) {
			logger.info(object);
		}
		//sqlSession.clearCache();
		
		//User user = new User();
		//user.setId(2);
		//user.setUsername("zlj");
		//user.setSex("男");
		//sqlSession.update("mybatis.updateUserById", user);
		
		List<Object> userListTwo = sqlSession.selectList("mybatis.queryAllUsers");
		logger.info("select second time");
		for (Object object : userListTwo) {
			logger.info(object);
		}
		
	}
 
	/**
	 * @Title: queryUserById
	 * @Description: 根据id查询用户(查询)
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void queryUserById() throws IOException {
		// 3.使用SqlSessionFactory对象，创建SqlSession对象
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		// 4.使用SqlSession对象，调用方法执行
		Object user = sqlSession.selectOne("mybatis.queryUserById", 24);
		System.out.println(user);
		// 5.释放资源
		sqlSession.close();
	}
 
	/**
	 * @Title: queryUserNameLike
	 * @Description: 方式一：根据用户名称模糊查询用户
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void queryUserByName1() throws IOException {
		// 3.使用SqlSessionFactory对象，创建SqlSession对象
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		// 4.ʹ使用SqlSession对象，调用方法执行
		List<Object> userList = sqlSession.selectList("mybatis.queryUserByName1", "%小明%");
		for (Object object : userList) {
			System.out.println(object);
		}
		// 5.释放资源
		sqlSession.close();
	}
 
	/**
	 * @Title: queryUserNameLike
	 * @Description: 根据用户名称模糊查询用户
	 * @param:
	 * @return: void
	 * @throws IOException
	 * @throws @author
	 *             BlackDragon
	 */
	@Test
	public void queryUserByName2() throws IOException {
		// 3.使用SqlSessionFactory对象，创建SqlSession对象
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		// 4.使用SqlSession对象，调用方法执行
		List<Object> userList = sqlSession.selectList("mybatis.queryUserByName2", "小明");
		for (Object object : userList) {
			System.out.println(object);
		}
		// 5.释放资源
		sqlSession.close();
	}
	
	
	@Test
	public void findUserById() throws IOException {
		SqlSession sqlSession1 = null;
		SqlSession sqlSession2 = null;
		try {
			// 获得SqlSession 
			sqlSession1 = openSession();
			sqlSession2 = openSession();
			
			//获得Mapper:动态代理生成UserMapper实现类
            UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);
            //默认一级缓存：相同SELECT与param，只查询一次
            System.out.println("=======================默认使用系统一级缓存=======================");
            userMapper.findByUserId(1);
            userMapper.findByUserId(1);
           //二级缓存commit才会有效
            sqlSession1.commit();
            System.out.println("=======================重新创建SqlSession=======================");
            sqlSession2 = openSession();
            UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
            userMapper2.findByUserId(1);
            //二级缓存commit才会有效
            sqlSession2.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            if (sqlSession1 != null) {
                //sqlSession生命周期是随着SQL查询而结束的
                sqlSession1.close();
            }
            
            if (sqlSession2 != null) {
                sqlSession2.close();
            }
        }

	}
}
