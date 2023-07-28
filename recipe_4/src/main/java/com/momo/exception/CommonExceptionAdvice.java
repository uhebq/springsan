package com.momo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

/**
 * root-context에 component-scan을 패키지에 적용해야 빈으로 등록!!
 * 
 * 🙂 ControllerAdvice
 * 	컨트롤러에 대한 예외를 처리하는 객체임을 명시
 * 	컨트롤러가 실행중 발생되는 오류 이므로 500번 오류가 발생 하는경우 실행됩니다.
 * 
 * 🙂 ExceptionHandler 
 * 	Controller, RestController가 적용된 Bean내에서 발생하는 예외를 
 * 	하나의 메서드에서 처리해주는 기능을 한다.
 * 
 */
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {


	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		System.out.println("Exception....." + ex.getMessage());
		log.info("Exception.....");
		log.debug("로그 테스트 + debug");
		log.error("로그 테스트 + error");
		
		model.addAttribute("exception", ex);
		
		return "/error/error500";
	}
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handel404(NoHandlerFoundException ex) {
		return "/error/error404";
	}
}













