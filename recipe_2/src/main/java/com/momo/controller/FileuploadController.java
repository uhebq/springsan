package com.momo.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@Controller
public class FileuploadController {

	/**
	 * 메서드의 리턴타입
	 * 
	 * 	1. String
	 * 		/WEB-INF/views/반환값.jsp 응답페이지 주소
	 *  	servlet-context.xml에 정의되어 있습니다.
	 *  
	 *  2. void
	 *  	요청주소와 동일한 이름의 jsp를 반환
	 */
	@GetMapping("/file/fileupload")
	public void fileupload() {
		
	}
	
	/**
	 * 파일업로드용 라이브러리 추가
	 * commons-fileupload
	 * 
	 * cos.jar와 달리 파일을 저장 하는로직이 추가 되어야 합니다!
	 * 
	 * 1. 라이브러리 추가
	 * 2. multipartResolver 빈 등록
	 * 3. 메서드의 매개변수로 MultipartFile이용
	 * @param mr
	 */
	@PostMapping("file/fileupload")
	public void fileuploadAction(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			System.out.println("===================================");
			System.out.println("oname : " + file.getOriginalFilename());
			System.out.println("name : " + file.getName());
			System.out.println("size : " + file.getSize());
			System.out.println("===================================");
		});
		
	}
}


















