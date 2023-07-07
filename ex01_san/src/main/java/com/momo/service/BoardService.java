package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momo.vo.BoardVo;

@Service
public interface BoardService {
	
	public List<BoardVo> getListXml();
	
	public int insert(BoardVo board); 

	public int insertSelectKey(BoardVo board);

	public BoardVo getOne(int bno);
	
	public int delete(int bno);
	
	public int update(BoardVo board);
	
	public int getTotalCnt();

}
