package com.momo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.momo.vo.Member;



@Component
public class MemberDao {

	public Member login(Member paramMember) {
		// System.out.println("memberdao");
		Member member = null;
		
		String sql = 
				String.format("select MNO, EMAIL, PW, NAME, NICKNAME, PNUM, REG_DATE, GRADE, DELYN from rec_member where email='%s' and pw='%s'"
						,paramMember.getEmail()
						,paramMember.getPw());
		
		// 쿼리 출력
		// System.out.println(sql);
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			// 질의결과 결과집합을 member객체에 담아줍니다
			if(rs.next()) {
				int mno = rs.getInt(1);
				System.out.println("mno" + mno);
				String name = rs.getString(4);
				String nickname = rs.getString(5);
				String pnum = rs.getString(6);
				Date reg_date = rs.getDate(7);
				String grade = rs.getString(8);
				boolean delyn = rs.getBoolean(9);
				
				
				member = new Member();
				member.setEmail(paramMember.getEmail());
				member.setName(name);
				System.out.println("name: " + name);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return member;
	}
}
