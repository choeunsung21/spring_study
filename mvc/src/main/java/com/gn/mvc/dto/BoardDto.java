package com.gn.mvc.dto;

import java.time.LocalDateTime;

import com.gn.mvc.entity.Board;
import com.gn.mvc.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDto {
	private Long board_no;
	private String board_title;
	private String board_content;
	private Long board_writer;
	private LocalDateTime reg_date;
	private LocalDateTime mod_date;
	
	// 1. BoardDto -> Board(Entity)
	public Board toEntity() {
		return Board.builder()
				.boardNo(board_no)
				.boardTitle(board_title)
				.boardContent(board_content)
				.member(Member.builder().memberNo(board_writer).build())
				.build();
	}
	
	// 2. Board(Entity) -> BoardDto
	public BoardDto toDto(Board board) {
		return BoardDto.builder()
				.board_no(board.getBoardNo())
				.board_title(board.getBoardTitle())
				.board_content(board.getBoardContent())
				.reg_date(board.getRegDate())
				.mod_date(board.getModDate())
				.build();
	}
}
