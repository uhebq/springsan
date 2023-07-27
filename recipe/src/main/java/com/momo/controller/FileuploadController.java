package com.momo.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.service.FileuploadService;
import com.momo.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@Log4j
public class FileuploadController extends CommonRestController{

	public static final String ATTACHES_DIR = "c:\\upload\\";

	@Autowired
	FileuploadService service;
	
	@GetMapping("/file/fileupload")
	public void fileupload() {
		
	}
	
	
	
	/**
	 * - 전달된 파일이 없는경우
	 * - enctype="multipart/form-data" 오타
	 * - 설정이 안되었을때
	 * 		라이브러리 추가(commons-fileupload)
	 * 		multipartResolver bean생성
	 *  
	 * @param files
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/file/fileuploadAction")
	public String fileuploadAction(List<MultipartFile> files
									, int bno
									, RedirectAttributes rttr) throws Exception {
		
		int insertRes = service.fileupload(files, bno);
		
		String msg = insertRes + "건 저장 되었습니다.";
		rttr.addAttribute("msg", msg);
		
		return "redirect:/file/fileupload";
	}
	
	@PostMapping("/file/fileuploadActionFetch")
	public @ResponseBody Map<String, Object> fileuploadActionFetch(List<MultipartFile> files
									, int bno) throws Exception {
		log.info("fileuploadActionFetch");
		int insertRes = service.fileupload(files, bno);
		log.info("업로드건수 : " + insertRes);
		return responseMap("success", insertRes + "건 저장 되었습니다.");
		
	}
	
	@GetMapping("/file/list/{bno}")
	public @ResponseBody Map<String, Object> fileuploadList
							(@PathVariable("bno") int bno) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getList(bno));
		
		return map;
	}
	


	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		String uploadPath 
				= currentDate.toString().replace("-", File.separator)
					+ File.separator;
		System.out.println("CurrentDate : " + currentDate);
		System.out.println("경로 : " + uploadPath);
	}
	
	

	
	@GetMapping("/file/delete/{uuid}/{bno}")
	public @ResponseBody Map<String, Object> delete(
								@PathVariable("uuid") String uuid
								, @PathVariable("bno") int bno) {
		
		int res = service.delete(bno, uuid);
		if(res>0) {
			return responseDeleteMap(res);
		} else {
			return responseDeleteMap(res);
		}
	}
	
	/**
	 * 파일다운로드
	 * 		컨텐츠타입을 다운로드 받을수 있는 형식으로 지정하여 
	 * 		브라우져에서 파을을 다운로드 할수 있게 처리
	 * @param fileName
	 * @return
	 */
	@GetMapping("/file/download")
	public @ResponseBody ResponseEntity<byte[]> download(String fileName){
		log.info("download file : " + fileName);
		HttpHeaders headers = new HttpHeaders();
		
		File file = new File(ATTACHES_DIR + fileName);
		
		if(file.exists()) {
			// 컨텐츠타입을 지정
			// APPLICATION_OCTET_STREAM : 이진 파일의 콘텐츠 유형
			headers.add("contentType"
					, MediaType.APPLICATION_OCTET_STREAM.toString());
			
			// 컨텐츠에 대한 추가설명및 파일이름 한글처리
			try {
				headers.add("Content-Disposition"
							, "attachment; filename=\"" 
								+ new String(file.getName().getBytes("UTF-8")
												,"ISO-8859-1") + "\"");
				return new ResponseEntity<>(
								FileCopyUtils.copyToByteArray(file)
								, headers
								, HttpStatus.OK
						);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(
								HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}













