package com.momo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.service.MemberService;
import com.momo.vo.Member;

@Controller
public class MemberController extends CommonRestController{
	
	@Autowired
	MemberService service;
	
	/**
	 * 로그인 페이지로 이동
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@PostMapping("/loginAction")
	
	//public String loginAction(Member member, Model model) {
	public @ResponseBody Map<String, Object> loginAction(
										@RequestBody Member member
										, Model model
										, HttpSession session){	
		System.out.println("email : " + member.getEmail());
		System.out.println("pw : " + member.getPw());
		
		member = service.login(member);
		
		if(member != null) {
			session.setAttribute("member", member);
			session.setAttribute("userEmail", member.getEmail());
			Map<String, Object> map = 
					responseMap(REST_SUCCESS, "로그인 되었습니다.");
			map.put("url","../main");
			
			return map;
		} else {
			return responseMap(REST_FAIL, "이메일, 비밀번호를 확인해주세요.");
		}
		
		// service.login(member, model);
		//model.addAttribute("message", member.getName() + "환영합니다.");
		//return "main";
	}
	
	@PostMapping("/emailCheck")
	public @ResponseBody Map<String, Object> 
						emailCheck(@RequestBody Member member){

		int res = service.emailCheck(member);
		
		if(res == 0){
			return responseMap(REST_SUCCESS, "사용가능한 아이디 입니다.");
		} else {
			return responseMap(REST_FAIL, "이미 사용중인 아이디 입니다.");
		}
		
	}
	
	@PostMapping("/nicknameCheck")
	public @ResponseBody Map<String, Object> 
						nicknameCheck(@RequestBody Member member){

		int res = service.nicknameCheck(member);
		
		if(res == 0){
			return responseMap(REST_SUCCESS, "사용가능한 닉네임 입니다.");
		} else {
			return responseMap(REST_FAIL, "이미 사용중인 닉네임 입니다.");
		}
		
	}
	
	@PostMapping("/register")
	public @ResponseBody Map<String, Object> 
						register(@RequestBody Member member) {
		try {
			System.out.println("member : " + member);
			int res = service.insert(member);
			return responseWriteMap(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return responseMap(REST_FAIL, "등록중 예외사항이 발생 하였습니다.");
		}
	}
}















