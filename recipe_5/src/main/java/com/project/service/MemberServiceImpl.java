package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.mapper.MemberMapper;
import com.project.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper membermapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	MailService mailservice;
	
	@Override
	public MemberVo login(MemberVo paramMember) {
		MemberVo member = membermapper.login(paramMember);
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
	public int insert(MemberVo member) {
		
		BCryptPasswordEncoder encoder 
						= new BCryptPasswordEncoder();
		
		// 비밀번호 암호화
		member.setPw(encoder.encode(member.getPw()));
		System.out.println("pw : " + member.getPw());
		return membermapper.insert(member);
	}

	@Override
	public int emailCheck(MemberVo member) {
		return membermapper.emailCheck(member);
	}
	
	@Override
	public int nicknameCheck(MemberVo member) {
		return membermapper.nicknameCheck(member);
	}
	
	@Override
	public String findEmailBy(MemberVo member) {
		return membermapper.findEmailBy(member);
	}
	
	@Override
	public String sendPwBy(MemberVo member, Model model) {
		String pw = membermapper.sendPwBy(member);
		
		if (pw != null) {
			// 임시 비밀번호 생성
			String temporaryPassword = PasswordGenerator.generateRandomPassword();

	        // 사용자 비밀번호 업데이트 (생성한 임시 비밀번호로 업데이트)
	        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedTemporaryPassword = passwordEncoder.encode(temporaryPassword);
	        membermapper.updatePassword(member.getEmail(), encodedTemporaryPassword);

			// 이메일 전송
	        member.setPw(temporaryPassword);
    		mailservice.findMailSend(member);
    		System.out.println(pw);
    		model.addAttribute("msg", "해당 이메일로 임시비밀번호가 발송되었습니다.");
    		return "recipe/send-pw-form";
    		
    	} else {
    		model.addAttribute("error", "해당 아이디와 이름으로 등록된 정보가 없습니다.");
    		return "recipe/send-pw-form";
    	}
	}

	@Override
	public boolean emailExists(String email) {
		return membermapper.emailExists(email);
	}

}	


















