package com.momo.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.MemberMapper;
import com.momo.vo.Member;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MemberTest {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void test() {
		Member member = new Member();
		member.setEmail("admin");
		member.setPw("1234");
		
		member = memberMapper.login(member);
		
		log.info(member);
		assertNotNull(member);
	}
	
	@Test
	public void testInsert() {
		Member member = new Member();
		member.setMno(3);
		member.setEmail("admin1");
		member.setPw("1234");
		member.setName("admin1");
		member.setPnum("010-1234-5678");
		int res = memberMapper.insert(member);		
		
		assertEquals(1, res);
	}
	
	@Test
	public void testEamilCheck() {
		Member member = new Member();
		member.setEmail("test");
		
		int res = memberMapper.emailCheck(member);		
		
		assertEquals(1, res);
	}
	
}















