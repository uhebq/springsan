package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momo.vo.Member;

@Service
public interface MemberService {
	Member login(Member member);
}
