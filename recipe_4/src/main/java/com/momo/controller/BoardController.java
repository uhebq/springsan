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
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	/**
	 * /board/msg
	 * WEB-INF/views/board/msg.jsp
	 */
	
	// board/reply/test
	@GetMapping("/reply/test")
	public String test() {
		return "/reply/test";
	}
	
	@GetMapping("msg")
	public void msg() {
		
	}
	
	@GetMapping("message")
	public void message(Model model) {
		
	}
	@GetMapping("list_bs")
	public void list_bs(Model model) {
		
	}
	@Autowired
	BoardService boardService;
	/**
	 * 파라메터의 자동수집
	 * 	기본생성자를 이용해서 객체를 생성
	 * 	-> setter 메서드를 이용해서 세팅
	 * @param model
	 * @param cri
	 */
	@GetMapping("list")
	public void getList(Model model, Criteria cri) {
		boardService.getListXml(cri, model);
		log.info("====================list");
		log.info("cri : " + cri);
		
	}
	
	@GetMapping("view")
	public void getOne(Model model, BoardVO paramVO) {
		log.info("================ bno" + paramVO);
		BoardVO board = boardService.getOne(paramVO.getBno());
		model.addAttribute("board", board);
	}
	
	/**
	 * requestMapping에 /board/ 가 설정 되어 있으므로
	 * /board/write
	 * @param model
	 */
	@GetMapping("write")
	public void write(Model model) {
		
	}
	
	/**
	 * 😀 RedirectAttributes
	 * 
	 * 리다이렉트 URL의 화면까지 데이터를 전달
	 * 
	 * Model과 같이 매개변수로 받아 사용
	 * addFlashAttribute : 세션에 저장후 페이지 전환
	 * 
	 */
	@PostMapping("write")
	public String writeAction(BoardVO board
								, RedirectAttributes rttr
								, Model model) {
		log.info(board);
		
		// 시퀀스 조회 후 시퀀스 번호를 bno에 저장
		int res = boardService.insertSelectKey(board);
		
		String msg = "";
		
		if(res > 0) {
			
			msg = board.getBno() + "번 등록되었습니다";
			// url?msg=등록 (쿼리스트링으로 전달 -> param.msg)
			//rttr.addAttribute("msg", msg);
			
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
	
	@GetMapping("edit")
	public String edit(BoardVO paramVO, Model model) {
		BoardVO board = boardService.getOne(paramVO.getBno());
		model.addAttribute("board", board);
		
		return "/board/write";
	}
	
	@PostMapping("editAction")
	public String editAction(BoardVO board
								, RedirectAttributes rttr
								, Model model) {
		// 수정
		int res = boardService.update(board);
		
		if(res > 0) {
			// redirect시 request 영역이 공유 되지 않으므로 
			// RedirectAttributes를 이용 합니다. 
			//model.addAttribute("msg", "수정 되었습니다.");
			rttr.addFlashAttribute("msg", "수정되었습니다.");
			
			// 상세페이지로 이동
			return "redirect:/board/view?bno=" + board.getBno();			
		} else {
			model.addAttribute("msg", "수정중 예외사항이 발생 하였습니다.");
			return "/board/message";
		}
		
	}
	
	@GetMapping("delete")
	public String delete(BoardVO board
							, RedirectAttributes rttr
							, Model model) {
		
		int res = boardService.delete(board.getBno());
		if(res > 0) {			
			rttr.addFlashAttribute("msg", "삭제되었습니다.");
			return "redirect:/board/list";
		} else {
			model.addAttribute("msg", "삭제중 예외가 발생 하였습니다.");
			return "/board/message";
		}
	}
}





















