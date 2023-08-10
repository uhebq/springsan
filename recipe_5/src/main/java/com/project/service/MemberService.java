package com.project.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.vo.MemberVo;

@Service
public interface MemberService {

	MemberVo login(MemberVo member);
	
	int insert(MemberVo member);
	
	int emailCheck(MemberVo member);

	int nicknameCheck(MemberVo member);

	String findEmailBy(MemberVo member);
	
	String sendPwBy(MemberVo member, Model model);

	boolean emailExists(String email);

	

}
