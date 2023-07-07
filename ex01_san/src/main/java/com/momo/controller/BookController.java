package com.momo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momo.service.BookService;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/book/*")
@Log4j
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("list")
	public void list(Criteria cri, Model model) {
		// 리스트 조회
		log.info("cri : " + cri);
		// 리스트 조회
		bookService.getList(cri, model);
		
		// 화면에 전달
		model.addAttribute("msg", "/book/list");
		// return "/book/list";
		// -> WEB-INF/views/book/list.jsp
	}
	
	@GetMapping("view")
	public void getOne(BookVO book, Model model) {
		bookService.getOne(book.getNo(), model);
	}
}
