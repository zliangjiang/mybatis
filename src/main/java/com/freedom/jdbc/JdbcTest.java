package com.freedom.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * Title: JdbcTest.java
 * Copyright: Copyright (c)2018
 * @author: BlackDragon
 * @date: 2018年10月29日
 */
public class JdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet set = null;
		try {
			/** 1.加载驱动 **/
			Class.forName("com.mysql.jdbc.Driver");
			/** 2.创建数据库的链接对象**/
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis", "root", "root");
			/** 3.定义sql语句**/
			String sql = "select * from user where id = ?";
			/** 4.创建statement对象**/
			psmt = con.prepareStatement(sql);
			/** 5.设置参数**/
			psmt.setInt(1, 10);
			/** 6.ִ执行**/
			set = psmt.executeQuery();
			/** 7.处理结果集**/
			while (set.next()) {
					System.out.println("用户Id:" + set.getInt("id") + ",用户名:" + set.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** 8.释放资源 **/
				if (set != null) {
					set.close();
				}
				
				if (psmt != null) {
					psmt.close();
				}
				
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
