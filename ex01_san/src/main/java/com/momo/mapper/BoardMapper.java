package com.momo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.momo.vo.BoardVo;

public interface BoardMapper {
	
	@Select("select * from tbl_board")
	public List<BoardVo> getList();

	public List<BoardVo> getListXml();
	
	public int insert(BoardVo board); 

	public int insertSelectKey(BoardVo board);

	public BoardVo getOne(int bno);
	
	public int delete(int bno);
	
	public int update(BoardVo board);
	
	public int getTotalCnt();

}
