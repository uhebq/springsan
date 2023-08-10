package com.project.mapper;

import org.apache.ibatis.annotations.Param;

import com.project.vo.MemberVo;

public interface MemberMapper {
	
	// 로그인
	public MemberVo login(MemberVo member);
	
	// 회원가입 정보 추가
	public int insert(MemberVo member);
	
	// 이메일 검증
	public int emailCheck(MemberVo member);
	
	// 닉네임 중복 검증
	public int nicknameCheck(MemberVo member);
	
	// 이메일 찾기
	public String findEmailBy(MemberVo member);
	
	// 임시 비밀번호 발급
	public String sendPwBy(MemberVo member);
	
	// 임시 비밀번호로 패스워드 변경
	void updatePassword(@Param("email") String email, @Param("encodedPassword") String encodedTemporaryPassword);
	
	// 회원가입시 이메일 인증
	public boolean emailExists(String email);
	
}
