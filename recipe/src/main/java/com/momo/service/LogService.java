package com.momo.service;

import org.springframework.stereotype.Service;

import com.momo.vo.LogVO;

@Service
public interface LogService {
	int insert(LogVO vo);
}
