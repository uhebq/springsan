package com.momo.mapper;

import org.apache.ibatis.annotations.Param;

import com.momo.vo.Member;

public interface MemberMapper {
	public Member login(Member member);
	public int insert(Member member);
	public int emailCheck(Member member);
	public int nicknameCheck(Member member);
	
	public String findEmailBy(Member member);
	public String findPwBy(Member member);
	public String sendPwBy(Member member);

	void updatePassword(@Param("email") String email, @Param("encodedPassword") String encodedTemporaryPassword);
	
//	public Member findByEmail(@Param("email") String email);
//	public void updatePassword(@Param("email") String email, @Param("password") String password);

//	public String getPwByIdNameRrn(Member member);
	
	public String resetPw(Member member);
	public String confirmEmailBy(Member member);
	
	public boolean emailExists(String email);
}