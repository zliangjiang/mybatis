package com.freedom.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
 
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
 
import com.freedom.mybatis.domain.User;
 
public class MyBatisTest {
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
		// 1.加载核心配置文件
		InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
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
		user.setId(4);
		user.setUsername("李诗诗");
		user.setBirthday(new Date());
		user.setSex("女");
		user.setAddress("来自大明朝");
		sqlSession.insert("mybatis.addUser", user);
		// 事务提交
		sqlSession.commit();
		// 释放资源
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
		sqlSession.delete("mybatis.deleteUser", 3);
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
		user.setId(1);
		user.setUsername("李师师和崇祯");
		user.setSex("1");
 
		sqlSession.update("mybatis.updateUserById", user);
		// 释放资源
		sqlSession.close();
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
	
	
	
}
