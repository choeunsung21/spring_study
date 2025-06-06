package com.gn.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gn.mvc.dto.AttachDto;
import com.gn.mvc.dto.BoardDto;
import com.gn.mvc.dto.PageDto;
import com.gn.mvc.dto.SearchDto;
import com.gn.mvc.entity.Attach;
import com.gn.mvc.entity.Board;
import com.gn.mvc.service.AttachService;
import com.gn.mvc.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private Logger logger = LoggerFactory.getLogger(BoardController.class);

	// 1. 필드 주입 -> 순환 참조
//	@Autowired
//	BoardService service;

	// 2. 메소드(Setter) 주입
//	private BoardService service;
//	
//	@Autowired
//	public void setBoardService(BoardService service) {
//		this.service = service;
//	}

	// 3. 생성자 주입 + final
	private final BoardService service;
	private final AttachService attachService;

//	@Autowired
//	public BoardController(BoardService service) {
//		this.service = service;
//	}

	@GetMapping("/board/create")
	public String createBoardView() {
		return "board/create";
	}

	@PostMapping("/board")
	@ResponseBody
	public Map<String, String> createBoardApi(
//			@RequestParam("board_title") String boardTitle,
//			@RequestParam("board_content") String boardContent
//			@RequestParam Map<String,String> param
			BoardDto dto) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "게시글 등록 중 오류가 발생하였습니다.");

		List<AttachDto> attachDtoList = new ArrayList<AttachDto>();

		for (MultipartFile mf : dto.getFiles()) {
			AttachDto attachDto = attachService.uploadFile(mf);
			if (attachDto != null)
				attachDtoList.add(attachDto);
		}

		int result = service.createBoard(dto, attachDtoList);
		if (result > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글이 등록되었습니다.");
		}

		// Service가 가지고 있는 createBoard 메소드 호출
//		BoardDto result =  service.createBoard(dto);
//		
//		logger.debug("1 : " + result.toString());
//		logger.info("2 : " + result.toString());
//		logger.warn("3 : " + result.toString());
//		logger.error("4 : " + result.toString());

		return resultMap;
	}

	@GetMapping("/board")
	public String selectBoardAll(Model model, SearchDto searchDto, PageDto pageDto) {

		if (pageDto.getNowPage() == 0)
			pageDto.setNowPage(1);

		// 1. DB에서 목록 SELECT
		Page<Board> resultList = service.selectBoardAll(searchDto, pageDto);

		pageDto.setTotalPage(resultList.getTotalPages());

		// 2. 목록 Model에 등록
		model.addAttribute("boardList", resultList);
		model.addAttribute("searcDto", searchDto);
		model.addAttribute("pageDto", pageDto);
		// 3. list.html에 데이터 세팅
		return "board/list";
	}

	@GetMapping("/board/{id}")
	public String selectBoardOne(@PathVariable("id") Long id, Model model) {
		logger.info("게시글 단일 조회 : " + id);
		Board result = service.selectBoardOne(id);
		model.addAttribute("board", result);
		List<Attach> attachList = attachService.selectAttachList(id);
		model.addAttribute("attachList", attachList);
		return "board/detail";
	}

	@GetMapping("/board/{id}/update")
	public String updateBoardView(@PathVariable("id") Long id, Model model) {
		Board board = service.selectBoardOne(id);
		model.addAttribute("board", board);

		List<Attach> attachList = attachService.selectAttachList(id);
		model.addAttribute("attachList", attachList);
		return "board/update";
	}

	@PostMapping("/board/{id}/update")
	@ResponseBody
	public Map<String, String> updateBoardApi(BoardDto param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "게시글 수정 중 오류가 발생했습니다.");

		List<AttachDto> attachDtoList = new ArrayList<AttachDto>();

		if (param.getFiles() != null && !param.getFiles().isEmpty()) {
			for (MultipartFile mf : param.getFiles()) {
				AttachDto attachDto = attachService.uploadFile(mf);
				if (attachDto != null)
					attachDtoList.add(attachDto);
			}
		}

		Board result = service.updateBoard(param, attachDtoList);
		if (result != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글이 수정되었습니다.");
		}

		return resultMap;
		// logger.info("삭제파일정보 : "+param.getDelete_files());

//		// 1. BoardDto 출력(전달 확인)
//		logger.info("게시글 수정 조회 : "+param);
//		
//		// 2. BoardService -> BoardRepository 게시글 수정
//		Board result = service.updateBoard(param);
//		
//		// 3. 수정 결과 Entity가 null이 아니면 성공 그외에는 실패
//		if(result != null) {
//			resultMap.put("res_code", "200");
//			resultMap.put("res_msg", "게시글 수정되었습니다.");
//		}
	}

	@DeleteMapping("/board/{id}/delete")
	@ResponseBody
	public Map<String, String> deleteBoardApi(@PathVariable("id") Long id) {
		logger.info("삭제확인 : " + id);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");

		int result = service.deleteBoard(id);
		if (result != 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 삭제가 완료되었습니다.");
		}
		return resultMap;
	}

}
