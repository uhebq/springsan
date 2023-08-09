package com.momo.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.MemberMapper;
import com.momo.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	MailService mailservice;
	
	@Override
	public Member login(Member paramMember) {
		Member member = memberMapper.login(paramMember);
		if(member != null) {
			// 사용자가 입력한 비밀번호가 일치하는지 확인
			// 사용자가 입력한 비밀번호, 데이터베이스에 암호화되어 저장된 비밀번호
			boolean res = 
					encoder.matches(paramMember.getPw()
									, member.getPw());
			
			// 비밀번호 인증이 성공하면 member객체를 반환
			if(res) {
			return member;
			}
		}
		
		return null;
	}
	
	@Override
	public int insert(Member member) {
		
		BCryptPasswordEncoder encoder 
						= new BCryptPasswordEncoder();
		
		// 비밀번호 암호화
		member.setPw(encoder.encode(member.getPw()));
		System.out.println("pw : " + member.getPw());
		return memberMapper.insert(member);
	}

	@Override
	public int emailCheck(Member member) {
		return memberMapper.emailCheck(member);
	}
	
	@Override
	public int nicknameCheck(Member member) {
		return memberMapper.nicknameCheck(member);
	}
	
	@Override
	public String findEmailBy(Member member) {
		return memberMapper.findEmailBy(member);
	}
	
	@Override
	public String findPwBy(Member member) {
		return memberMapper.findPwBy(member);
	}

	@Override
	public String sendPwBy(Member member, Model model) {
		String pw = memberMapper.sendPwBy(member);
		
		if (pw != null) {
			// 임시 비밀번호 생성
			String temporaryPassword = PasswordGenerator.generateRandomPassword();

	        // 사용자 비밀번호 업데이트 (생성한 임시 비밀번호로 업데이트)
	        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedTemporaryPassword = passwordEncoder.encode(temporaryPassword);
	        memberMapper.updatePassword(member.getEmail(), encodedTemporaryPassword);

			// 이메일 전송
	        member.setPw(temporaryPassword);
    		mailservice.findMailSend(member);
    		System.out.println(pw);
    		model.addAttribute("msg", "해당 이메일로 임시비밀번호가 발송되었습니다.");
    		return "send-pw-form";
    		
    	} else {
    		model.addAttribute("error", "해당 아이디와 이름으로 등록된 정보가 없습니다.");
    		return "send-pw-form";
    	}
	}

	@Override
	public String confirmEmailBy(Member member, Model model) {
		String email = memberMapper.confirmEmailBy(member);
		if (email == null) {
			// 가입되지 않은 이메일이므로 발송 로직을 실행
	        String verificationCode = ConfirmEmailGenerator.generateRandomVerificationCode();
	        mailservice.confirmEmail(email, verificationCode);
    		System.out.println(email);
    		model.addAttribute("msg", "해당 이메일로 인증번호가 발송되었습니다.");
    		model.addAttribute("verificationCode", verificationCode); // 인증번호 전달
    	} else {
    		model.addAttribute("error", "이미 가입된 아이디입니다.");
    	}
		return ""; // 화면이동을 명시적으로 반환하지 않음
	}

	@Override
	public boolean emailExists(String email) {
		return memberMapper.emailExists(email);
	}

}	
//	public class MemberService {
//
//	    @Autowired
//	    private MemberMapper memberMapper;
//
//	    @Autowired
//	    private MailService mailService;
//
//	    @Autowired
//	    private PasswordEncoder passwordEncoder;
//
//	    public void resetPasswordByEmail(String email) {
//	        // 이메일로 회원 정보 조회
//	        Member member = memberMapper.findByEmail(email);
//
//	        if (member != null) {
//	            // 새로운 임시 비밀번호 생성
//	            String temporaryPassword = generateTemporaryPassword();
//	            // 임시 비밀번호를 암호화하여 회원 정보에 저장
//	            String encodedPassword = passwordEncoder.encode(temporaryPassword);
//	            memberMapper.updatePassword(email, encodedPassword);
//	            // 임시 비밀번호 이메일로 발송
//	            mailService.sendTemporaryPasswordByEmail(email, temporaryPassword);
//	        } else {
//	            // 회원 정보가 없으면 예외 처리 또는 적절한 로직 추가
//	        }
//	    }
//
//	    private String generateTemporaryPassword() {
//	        return RandomStringUtils.randomAlphanumeric(8);
//	    }
//	}

	
//	@Override
//	public String getPwByIdNameRrn(Member member) {
//		return memberMapper.getPwByIdNameRrn(member);
//	}
	
//	@Override
//    public String resetPw(Member member) {
//		return memberMapper.resetPw(member);
//	}
//	
//	    @Autowired
//	    private MemberRepository memberRepository;
//
//	    @Override
//	    public String resetPw(String email) {
//	        // 이메일로 회원 정보 조회
//	        Member member = memberRepository.findByEmail(email);
//
//	        if (member != null) {
//	            // 임시 비밀번호 생성 (이 부분은 실제로는 보안을 고려하여 더 복잡한 방식으로 생성해야 합니다.)
//	            String temporaryPassword = generateTemporaryPassword();
//
//	            // 회원의 비밀번호를 임시 비밀번호로 변경
//	            member.setPassword(encodePassword(temporaryPassword));
//	            memberRepository.save(member);
//
//	            return temporaryPassword;
//	        } else {
//	            // 회원 정보가 없으면 null 반환 또는 오류 처리
//	            return null;
//	        }
//	    }
//
//	    // 임시 비밀번호 생성 메서드 (실제로는 더 강력한 랜덤한 방식으로 생성해야 함)
//	    private String generateTemporaryPassword() {
//	        return UUID.randomUUID().toString().substring(0, 8); // 8자리 임시 비밀번호 생성
//	    }
//
//	    // 비밀번호 암호화 메서드 (Spring Security의 BCryptPasswordEncoder 사용 등)
//	    private String encodePassword(String password) {
//	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	        return passwordEncoder.encode(password);
//	    }
//	}
//
//}


















