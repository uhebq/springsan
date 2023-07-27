package com.momo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.vo.Member;

@Service
public interface MemberService {
	
	Member login(Member member);
	
	int insert(Member member);
	
	int idCheck(Member member);

	void naverLogin(HttpServletRequest request, Model model);
}
