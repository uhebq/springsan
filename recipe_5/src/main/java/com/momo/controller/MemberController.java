package com.momo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.service.MailService;
import com.momo.service.MemberService;
import com.momo.vo.Member;

@Controller
public class MemberController extends CommonRestController{
	
	@Autowired
	MemberService service;
	
	@Autowired
	MailService mailservice;
	
	/**
	 * 로그인 페이지로 이동
	 * @return
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}
	
	@PostMapping("/loginAction")
	
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
			map.put("url","/main");
			
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
	
	// 이메일 인증번호 발송
	@PostMapping("/confirmEmail")
	public ResponseEntity<Map<String, Boolean>> confirmEmail(HttpSession session, @RequestBody Map<String, String> requestData) {
	    String email = requestData.get("email");
	    boolean emailExists = service.emailExists(email);
	    
	    if (emailExists) {
	    	return ResponseEntity.ok(Map.of("exists", true));
	    } else {
            String verificationCode = mailservice.ConfirmEmailGenerator();
            mailservice.confirmEmail(email, verificationCode);
            session.setAttribute("verificationCode", verificationCode);
            return ResponseEntity.ok(Map.of("success", true));
	    }
	}
	
	// 이메일 인증번호 검증
	@PostMapping("/verifyConfirmationCode")
	public ResponseEntity<Map<String, String>> verifyConfirmationCode(HttpSession session, @RequestBody Map<String, String> requestData) {
	    String confirmCode = requestData.get("confirmCode");
	    String verificationCode = (String) session.getAttribute("verificationCode");
	    System.out.println("confirmCode : " + confirmCode);
	    System.out.println("verificationCode : " + verificationCode);
	    
	    if(verificationCode != null && verificationCode.equals(confirmCode)) {
	        return ResponseEntity.ok(Map.of("success", "success"));
	    } else {
	        return ResponseEntity.ok(Map.of("fail", "fail"));
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
	
    // 아이디 찾기 폼을 보여주는 페이지로 이동
    @GetMapping("/findEmailForm")
    public String showFindEmailForm() {
        return "find-email-form"; // find-email-form.jsp 페이지로 이동
    }

    @PostMapping("/findEmail")
    public String findEmail(Member member, Model model) {
    	// 아이디 찾기 로직을 구현
    	String email = service.findEmailBy(member);
    	if (email != null) {
    		model.addAttribute("foundEmail", email);
    		//System.out.println(email);
    		return "find-email-form";
    	} else {
    		model.addAttribute("error", "해당 이름과 전화번호로 등록된 이메일이 없습니다.");
    		return "find-email-form";
    	}
    }
    
    @GetMapping("/sendPwForm")
    public String showsendPwForm() {
        return "send-pw-form"; // send-pw-form.jsp 페이지로 이동
    }

    @PostMapping("/sendPw")
    public String sendPw(Member member, Model model) {
    	// 아이디 찾기 로직을 구현
    	return service.sendPwBy(member, model);
    }
    
}
    
    
    
        
        

    
    
    















