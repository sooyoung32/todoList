package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import service.BoardService;
import vo.Board;
import vo.BoardPage;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service; 
	
	
	//페이지 처리
	public static final int NUM_OF_BOARD = 3;
	@RequestMapping(value="boardList.do", method=RequestMethod.GET)
	public ModelAndView getBoardPage(int page) {
		ModelAndView mv = new ModelAndView("board_list");
		
		int totalBoardCount = service.selectBoardCount();
		int startPage = 0;
		int endPage = 0;
		int startRow = 0;
		int endRow = 0;
		int totalPage = 0;
		List<Board> boardList;

		if (totalBoardCount > 0) {
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;
			boardList = service.selectBoardList(startRow, endRow);

			totalPage = totalBoardCount / NUM_OF_BOARD;
			if (totalBoardCount % NUM_OF_BOARD != 0) {
				totalPage++;
			}

			startPage = page - 10;
			if (startPage < 0) {
				startPage = 1;
			}

			endPage = page + 10;
			if (endPage > totalPage) {
				endPage = totalPage;
			}

			
			BoardPage boardPage = new BoardPage(boardList, startPage, endPage, startRow,
					endRow, totalPage);
			mv.addObject("boardPage", boardPage);
			return mv; 
			
		} else {
			BoardPage boardPage =  new BoardPage(Collections.<Board> emptyList(), 0, 0, 0, 0, 0);
			mv.addObject("boardPage", boardPage);
			return mv;
		}
	}
	
	
	@RequestMapping(value="read.do", method=RequestMethod.GET)
	public ModelAndView readBoard(int boardNo, boolean isHitCount){
		Board board = service.selectBoardByBoardNo(boardNo, isHitCount);
		ModelAndView mv = new ModelAndView("read");
		mv.addObject("board",board);
		return mv;
	}
	
	
	
	
}
