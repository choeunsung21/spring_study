package com.gn.mvc.service;

import org.springframework.stereotype.Service;

import com.gn.mvc.dto.BoardDto;
import com.gn.mvc.entity.Board;
import com.gn.mvc.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
//	@Autowired
//	BoardRepository repository;
	
	private final BoardRepository repository;
	
	public BoardDto createBoard(BoardDto dto) {
		// 1. 매개변수 dto -> entity
		//Board param = Board.builder()
		//		.boardTitle(dto.getBoard_title())
		//		.boardContent(dto,getBoard_content)
		//		.build();
		Board param = dto.toEntity();
		// 2. Repository 의 save() 메소드 호출
		Board result = repository.save(param);
		// 3. 결과 entity -> dto
		return new BoardDto().toDto(result);
	}

}
