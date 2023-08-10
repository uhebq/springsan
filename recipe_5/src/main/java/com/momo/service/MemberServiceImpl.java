package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean emailExists(String email) {
		return memberMapper.emailExists(email);
	}

}	


















