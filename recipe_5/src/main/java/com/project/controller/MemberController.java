package com.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.service.MailService;
import com.project.service.MemberService;
import com.project.vo.MemberVo;

@Controller
@RequestMapping("/recipe/*")
public class MemberController{
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	MailService mailservice;
	
	/**
	 * 로그인 페이지로 이동
	 * @return
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "/recipe/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/main")
	public String main() {
		return "recipe/main";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "recipe/register";
	}
	
	@PostMapping("/loginAction")
	public @ResponseBody Map<String, Object> loginAction(
										@RequestBody MemberVo member
										, Model model
										, HttpSession session){	
		System.out.println("email : " + member.getEmail());
		System.out.println("pw : " + member.getPw());
		
		member = memberservice.login(member);
		
		if(member != null) {
			session.setAttribute("member", member);
			session.setAttribute("userEmail", member.getEmail());
			Map<String, Object> map = 
					responseMap(REST_SUCCESS, "로그인 되었습니다.");
			map.put("url","/recipe/main");
			
			return map;
		} else {
			return responseMap(REST_FAIL, "이메일, 비밀번호를 확인해주세요.");
		}
		
		// service.login(member, model);
		//model.addAttribute("message", member.getName() + "환영합니다.");
		//return "main";
	}
	
	private final String REST_WRITE = "등록";
	protected final String REST_SUCCESS = "success";
	protected final String REST_FAIL = "fail";
	
	public Map<String, Object> responseMap(int res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(res > 0) {
			map.put("result", REST_SUCCESS);
			map.put("msg", msg + " 되었습니다.");
		} else {
			map.put("result", REST_FAIL);
			map.put("msg", msg + "중 예외가 발생하였습니다.");
		}
		
		return map;
	}
	
	public Map<String, Object> responseWriteMap(int res){
		return responseMap(res, REST_WRITE);
	}
	
	public Map<String, Object> responseMap(String result
													, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", result);
		map.put("msg", msg);
		
		return map;
	}
	
	@PostMapping("/emailCheck")
	public @ResponseBody Map<String, Object> 
						emailCheck(@RequestBody MemberVo member){

		int res = memberservice.emailCheck(member);
		
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
	    boolean emailExists = memberservice.emailExists(email);
	    
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

	// 닉네임 중복 체크
	@PostMapping("/nicknameCheck")
	public @ResponseBody Map<String, Object> 
						nicknameCheck(@RequestBody MemberVo member){

		int res = memberservice.nicknameCheck(member);
		
		if(res == 0){
			return responseMap(REST_SUCCESS, "사용가능한 닉네임 입니다.");
		} else {
			return responseMap(REST_FAIL, "이미 사용중인 닉네임 입니다.");
		}
		
	}
	
	// 회원가입
	@PostMapping("/register")
	public @ResponseBody Map<String, Object> 
						register(@RequestBody MemberVo member) {
		try {
			System.out.println("member : " + member);
			int res = memberservice.insert(member);
			return responseWriteMap(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return responseMap(REST_FAIL, "등록중 예외사항이 발생 하였습니다.");
		}
	}
	
    // 아이디 찾기 폼을 보여주는 페이지로 이동
    @GetMapping("/findEmailForm")
    public String showFindEmailForm() {
        return "recipe/find-email-form"; // find-email-form.jsp 페이지로 이동
    }

    @PostMapping("/recipe/findEmail")
    public String findEmail(MemberVo member, Model model) {
    	// 아이디 찾기 로직을 구현
    	String email = memberservice.findEmailBy(member);
    	System.out.println("email : " + email);
    	if (email != null) {
    		model.addAttribute("foundEmail", email);
    		//System.out.println(email);
    		return "recipe/find-email-form";
    	} else {
    		model.addAttribute("error", "해당 이름과 전화번호로 등록된 이메일이 없습니다.");
    		return "recipe/find-email-form";
    	}
    }
    
    // 임시 비밀번호 발급 폼을 보여주는 페이지로 이동
    @GetMapping("/sendPwForm")
    public String showsendPwForm() {
        return "recipe/send-pw-form"; // send-pw-form.jsp 페이지로 이동
    }

    @PostMapping("/recipe/sendPw")
    public String sendPw(MemberVo member, Model model) {
    	// 아이디 찾기 로직을 구현
    	return memberservice.sendPwBy(member, model);
    }
    
}
    
    
    
        
        

    
    
    















