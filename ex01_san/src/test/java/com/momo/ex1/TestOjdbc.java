package com.momo.ex1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class TestOjdbc {
	
	@Test
	public void calcTest() {
		Calc calc = new Calc();
		int res = calc.add(1, 2);
		
		// 예상 결과값, 실제 결과 값
		assertEquals(4, res);
	}
	
	@Test
	public void ojdbcTest() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","library","1234");
			
			ResultSet rs=conn.createStatement().executeQuery("select to_char(sysdate, 'yyyy-mm-dd') today from dual");
			rs.next();
			System.out.println(rs.getString(1));
			System.out.println(conn);
			
			assertNotNull(conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
