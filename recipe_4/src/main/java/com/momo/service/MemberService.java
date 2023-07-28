package com.momo.service;

import org.springframework.stereotype.Service;

import com.momo.vo.Member;

@Service
public interface MemberService {

	Member login(Member member);
	
	int insert(Member member);
	
	int emailCheck(Member member);
}
