package com.momo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.vo.SimpleMDE;

@Controller
public class AdminController {
	
	@GetMapping("/admin")
	public String admin() {
		return "/admin/main";
	}
	
	
	@GetMapping("/simpleMDE/dropzone")
	public void dropzone() {
		
	}
	@GetMapping("/simpleMDE/simpleMDE")
	public void simpleMDE() {
		
	}
	
	@PostMapping("/simpleMDE/simpleMDEAction")
	public @ResponseBody Map<String, String> simpleMDEAction(@RequestBody SimpleMDE simpleMDE) {
		
        // Java로 작성된 문서를 처리하는 로직을 구현합니다.
        // 여기서는 단순히 로그로 출력하는 예시를 보여드리겠습니다.
        System.out.println("Received document content from client:");
        System.out.println(simpleMDE.getContent());

        // 클라이언트에 응답 전송
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", "Document received successfully!");
        map.put("content", simpleMDE.getContent());
        return map;
	}
}
