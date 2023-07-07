package com.momo.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.BoardMapper;
import com.momo.vo.BoardVo;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class boardTest {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Test
	public void getList() {
		assertNotNull(boardMapper);
		List<BoardVo> list = boardMapper.getList();
		
		list.forEach(board ->{
			log.info("boardVo================");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
			
		});
	}
	
	@Test
	public void getListXml() {
		List<BoardVo> list = boardMapper.getListXml();
		
		list.forEach(board ->{
			log.info("boardVoXML================");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
			
		});
	}
	
	@Test
	public void insert() {
		
		BoardVo board = new BoardVo();
		board.setTitle("제목");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.insert(board);
		
		assertEquals(res, 1);
		
	}
	
	@Test
	public void insertSelectKey() {
		BoardVo board = new BoardVo();
		board.setTitle("제목");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.insertSelectKey(board);
		log.info("bno : " + board.getBno());
		assertEquals(res, 1);
	}
	
	@Test
	public void getOne() {
		BoardVo board = boardMapper.getOne(10);
		System.out.println("==================");
		log.info(board);
	}
	
	@Test
	public void delete() {
		int res = boardMapper.delete(9);
		assertEquals(res, 1);
	}
	
	@Test
	public void update() {
		int bno = 11;
		
		BoardVo board = new BoardVo();
		board.setBno(bno);
		board.setTitle("제목 수정수정수정");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.update(board);
		
		BoardVo getBoard = boardMapper.getOne(7);
		
		assertEquals("제목 수정수정수정", getBoard.getTitle());
	}
	
	@Test
	public void getTotalCnt() {
		int res = boardMapper.getTotalCnt();
		
		log.info("totalCnt : " + res);
	}
}
