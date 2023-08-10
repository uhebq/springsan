package com.momo.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.vo.Member;

@Service
public interface MemberService {

	Member login(Member member);
	
	int insert(Member member);
	
	int emailCheck(Member member);

	int nicknameCheck(Member member);

	String findEmailBy(Member member);
	
	String sendPwBy(Member member, Model model);

	boolean emailExists(String email);

	

}
