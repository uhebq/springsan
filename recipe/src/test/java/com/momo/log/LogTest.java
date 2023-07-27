package com.momo.log;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.LogMapper;
import com.momo.vo.LogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class LogTest {
	
	@Autowired
	LogMapper logMapper;
	
	@Test
	public void test() {
		LogVO vo = new LogVO();
		vo.setClassName("className");
		vo.setMethodName("methodName");
		vo.setErrmsg("errmsg");
		vo.setParams("params");
		int res = logMapper.insert(vo);
		
		System.out.println("res : " + res);
		assertEquals(1, res);
	}
	
}













