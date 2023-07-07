package com.momo.book;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.BookService;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookServiceTest {

	@Autowired
	BookService bookService;
	
	@Test
	public void getList() {
		//assertNotNull(bookMapper);
		List<BookVO> list = bookService.getList(new Criteria(), null);
		log.info(list);
	}
	
	@Test
	public void getOne() {
		BookVO book = bookService.getOne(1193, null);
		log.info(book);
		
	}
}















