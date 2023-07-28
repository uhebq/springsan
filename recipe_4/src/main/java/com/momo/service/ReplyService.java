package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momo.vo.ReplyVO;

@Service
public interface ReplyService {
	
	List<ReplyVO> getList(int bno);
	
	int insert(ReplyVO vo);
	
	int delete(int rno);
}
