package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.dao.MemberDao;
import com.momo.vo.Member;

@Service
public class MemberService {
	
	@Autowired
	MemberDao dao;
	
	public Member login(Member paramMember, Model model) {
	 	Member member = dao.login(paramMember);
	 	if(member == null) {
	 		model.addAttribute("message", "아이디/비밀번호 확인");
	 	} else {
	 		model.addAttribute("message", member.getName()+"님 환영");
	 	}
	 	return member;
	}
}
