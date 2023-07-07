package com.momo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.service.BoardService;
import com.momo.vo.BoardVo;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	@GetMapping("message")
	public void message(Model model) {

	}

	@GetMapping("msg")
	public void msg() {

	}

	@GetMapping("list_bs")
	public void list_bs(Model model) {

	}

	@Autowired
	BoardService boardService;

	@GetMapping("list")
	public void getlist(Model model) {
		List<BoardVo> list = boardService.getListXml();
		Log.info("=============================");
		Log.info(list);

		model.addAttribute("list", list);
	}

	@GetMapping("view")
	public void getOne(Model model, int bno) {
		Log.info("======================== bno" + bno);
		model.addAttribute("board", boardService.getOne(bno));
	}

	@GetMapping("write")
	public void write(Model model) {
		BoardVo board = new BoardVo();
	}

	/**
	 * 😀 RedirectAttributes
	 * 
	 * 리다이렉트 URL의 화면까지 데이터를 전달
	 * 
	 * Model과 같이 매개변수로 받아 사용 addFlashAttribute : 세션에 저장후 페이지 전환
	 * 
	 * 
	 * 
	 */

	@PostMapping("write")
	public String writeAction(BoardVo board, RedirectAttributes rttr, Model model) {
		log.info(board);

		// 시퀀스 조회 후 시퀀스 번호를 bno에 저장
		int res = boardService.insertSelectKey(board);

		String msg = "";

		if (res < 0) {

			msg = board.getBno() + "번 등록되었습니다";
			// url?msg=등록 (쿼리스트링으로 전달 -> param.msg)
			// rttr.addAttribute("msg", msg);

			// 세션영역에 저장 -> msg
			// 새로고침시 유지되지 않음
			rttr.addFlashAttribute("msg", msg);

			return "redirect:/board/list";

		} else {
			msg = "등록중 예외사항이 발생 하였습니다.";
			model.addAttribute("msg", msg);
			return "/board/message";
		}

	}
}
