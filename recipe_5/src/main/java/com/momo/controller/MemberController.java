package com.momo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.mapper.MemberMapper;
import com.momo.service.MailService;
import com.momo.service.MailService2;
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
	
	@PostMapping("/confirmEmail")
	public ResponseEntity<Map<String, String>> confirmEmail(@RequestBody Map<String, String> requestData) {
	    String email = requestData.get("email");
	    
	    if (service.emailExists(email)) {
	        String verificationCode = mailservice.ConfirmEmailGenerator();
	         mailservice.confirmEmail(email, verificationCode);
	        return ResponseEntity.ok(Map.of("success", "true"));
	    } else {
	        return ResponseEntity.ok(Map.of("success", "false"));
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
    
    @GetMapping("/findPwForm")
    public String showFindPwForm() {
        return "find-pw-form"; // find-email-form.jsp 페이지로 이동
    }

    @PostMapping("/findPw")
    public String findPw(Member member, Model model) {
    	// 아이디 찾기 로직을 구현
    	String pw = service.findPwBy(member);
    	if (pw != null) {
    		model.addAttribute("foundPw", pw);
    		System.out.println(pw);
    		return "find-pw-form";
    	} else {
    		model.addAttribute("error", "해당 아이디와 이름으로 등록된 정보가 없습니다.");
    		return "find-pw-form";
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
    
    
    
    
    @Autowired
    private MailService2 mailService2;

    @GetMapping("/getVerificationCode")
    public String showVerificationCodePage() {
        return "verification-code";
    }

    @PostMapping("/sendVerificationCode")
    public String sendVerificationCode(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        String verificationCode = mailService2.generateRandomCode(6);
        mailService2.sendVerificationCodeByEmail(email, verificationCode);
        redirectAttributes.addFlashAttribute("message", "인증번호가 이메일로 발송되었습니다.");
        return "verify-code";
    }
    
//    @GetMapping("/verifyCode")
//    public String showVerifyCodePage() {
//        return "verify-code";
//    }

    @PostMapping("/checkVerificationCode")
    public String checkVerificationCode(@RequestParam("code") String code, Model model) {
        // 여기에서 사용자가 입력한 인증번호를 검증하는 로직을 추가하세요.
        // 예를 들어, 사용자가 입력한 인증번호와 발급한 인증번호를 비교하고 결과를 처리합니다.
        if ("123456".equals(code)) {
            model.addAttribute("message", "인증되었습니다.");
        } else {
            model.addAttribute("message", "인증번호가 올바르지 않습니다.");
        }
        return "verify-code-result";
    }
}    
//    @Controller
//    public class PasswordResetController {
//
//        // 비밀번호 찾기 페이지 렌더링
//        @GetMapping("/forgotPassword")
//        public String showForgotPasswordPage() {
//            return "forgot-password";
//        }
//
//        // 비밀번호 찾기 처리
//        @PostMapping("/forgotPassword")
//        public String processForgotPassword(@RequestParam("email") String email, Model model) {
//            service.resetPasswordByEmail(email);
//            model.addAttribute("message", "임시 비밀번호가 이메일로 발급되었습니다.");
//            return "forgot-password-result";
//        }
//    }

    
//    @RequestMapping(value=command, method = RequestMethod.POST)
//    public ModelAndView doAction(Member member, HttpServletResponse response) throws IOException {
//    	
//    	String pw = MemberService.getPwByIdNameRrn(member, response);
//    	System.out.println("searchPw : " + pw);
//    }
//    
//    //pw 찾기
//    public String getPwBYNameRrn(MemberBean member, HttpServletResponse response) throws IOException {
//    	Strong email1="", email2="", pw="", msg="";
//    	// 비번찾기 폼에서 입력한 정보를 이용해 고객의 모든 정보를 불러와 mem에 넣는다.
//    	List<MemberBean> mem = sqlSessionTemplate.selectList(namespace+".GetPwById", member);
//    	for(MemberBean m : mem) {
//    		email1 = m.getEmail1();
//    		email2 = m.getEamil2();
//    	}
//    	member.setEmail1(email1);
//    	member.setEmail2(email2);
//    	
//    	response.setContentType("text/html;charset=utf-8");
//    	PrintWriter out = response.getWriter();
//    	// 가입된 아이디가 없으면
//    	if(mem.size() == 0) {
//    		out.println("<script>alert('회원 정보를 찾을 수 없음.(미등록 회원)'); location.href='start.jsp;</script>");
//    		out.close();
//    	} else { // 가입된 회원이면 임시 비번을 발급
//    		for (int i = 0; i<10; i++) {
//    			pw += (char)((Math.random() * 48) + 58);
//    		}
//    	member.setPassword(pw);
//    	updatePw(member);
//    	sendEmail(member,"findpw");
//    	
//    	out.print("<script>alert('네이버 메일로 임시 비밀번호를 발송하였습니다.'); location.href='start.jsp';</script>");
//    	out.flush();
//    	}
//    	return pw;
//    }
//    
//    public void sendEmail(MemberBean bean, String div) {
//    	String charSet = "utf-8";
//    	String hostSMTP = "smtp.naver.com"; //gmail 이용시 smtp.gmail.com
//    	String hostSMTPid = ""; // 서버 이메일 주소(보내는 사람 이메일 주소)
//    	String hostSMTPpwd = ""; // 서버 이메일 비번(보내는 사람 이메일 비번)
//    	
//    	// 보내는 사람 Email, 제목, 내용
//    	String fromEmail = ""; // 보내는 사람 이메일 주소(받는 사람 이메일에 표시됨)
//    	String fromName = ""; // 프로젝트 이름 또는 보내는 사람 이름
//    	String subject = "", msg ="";
//    	
//    	if(div.equals("findpw")) { // findpw 문자열을 보내서 div로 받았었음.
//    		subject = "임시 비밀번호 입니다.";
//    		msg += "<div align = 'center' style='border:1px soild black; font-family:verdana'>";
//    		msg += "<h3 style='color : blue;'>";
//    		msg += bean.getName() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
//    		msg += "<p>임시 비밀번호 : ";
//    		msg += bean.getPassword() + "</p></div>";
//    	}
//    	
//    	// 받는 사람 Email 주소
//    	String mail = bean.getEmail()+"@"+bean.getEmail2();
//    	try {
//    		HtmlEmail email = new HtmlEmail();
//    		email.setDebug(true);
//    		email.setCharset(charSet); // 한글 세팅
//    		email.setSSL(true);
//    		email.setHostName(hostSMTP);
//    		email.setSmtpPort(587); // gmail일 경우 465
//    		
//    		email.setAuthentication(hostSMTPid, hostSMTPpwd);
//    		email.setTLS(true);
//    		email.addTo(mail, charSet);
//    		email.setFrom(fromEmail, fromName, charSet);
//    		email.setSubject(subject);
//    		email.setHtmlMsg(msg);
//    		email.send(); // 메일발송
//    	} catch (Exception e) {
//    		System.out.println("메일 발송 실패 : " + e);
//    	}
//    }
    
//    @Autowired
//        private MailService mailService;
//
//        @PostMapping("/resetPassword")
//        public String resetPassword(@RequestParam("email") Member member, Model model) {
//            // 이메일로 회원 정보를 조회하여 임시 비밀번호를 발급하고, 메일로 전송하는 로직 구현
//            String temporaryPassword = service.resetPasswordByEmail(member);
//
//            // 메일 전송
//            mailService.sendTemporaryPassword(member, temporaryPassword);
//
//            model.addAttribute("message", "임시 비밀번호가 발급되었습니다. 이메일을 확인해주세요.");
//            return "reset-password-result"; // 임시 비밀번호 발급 결과를 보여줄 뷰 이름
//        }
        
        

    
    
    















