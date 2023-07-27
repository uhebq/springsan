package com.momo.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Lombok 라이브러리
 * getter/setter, equals, toString등의 메서드를 자동 생성 해줍니다.
 * 
 * Data 어노테이션
 * IDE(이클립스,STS)에 설치 후 롬복라이브러리를 추가후 사용 가능
 * IDE에 설치가 되어 있지 않으면 어노테이션을 추가 해도 메서드가 생성되지 않을수 있습니다.
 * Outline View를 통해 매서드가 생성되었는지 확인해주세요! 
 * 
 */
@Data
public class Member {
	private String id;
	private String pw;
	private String name;
	private int age;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date dueDate;
	
}











