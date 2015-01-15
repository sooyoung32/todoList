package controller;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import service.BoardService;
import service.CommentService;
import service.FileService;
import vo.Board;
import vo.BoardPage;
import vo.Comment;
import vo.UploadFile;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private FileService fileServie;
	@Autowired
	private CommentService commentService;

	private Logger logger = Logger.getLogger(BoardController.class);

	// 페이지 처리
	public static final int NUM_OF_BOARD = 15;

	@RequestMapping(value = "boardList.do")
	public ModelAndView getBoardPage(@RequestParam(value = "page", defaultValue = "1") int page, String searchKey,
			String searchValue) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board_list");

		int totalBoardCount;
		int startPage = 0;
		int endPage = 0;
		int startRow = 0;
		int endRow = 0;
		int totalPage = 0;
		int prePage = 0;
		int nextPage = 0;
		int pageNo = 0;
		List<Board> boardList;

		if (searchKey != null && searchValue != null) {
			totalBoardCount = boardService.searchBoardCount(searchKey, searchValue);
			// System.out.println("리스트 searchKey//"+searchKey +"//searchValue//"+searchValue);
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);

		} else {
			totalBoardCount = boardService.selectBoardCount();
			// System.out.println("검색X//"+totalBoardCount);
		}

		System.out.println("totCnt =" + totalBoardCount);
		if (totalBoardCount > 0) { // 게시글이 있는 경우

			pageNo = page;

			// 게시글 순서 0-9, 10-19
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;

			// 리스트 받기
			boardList = boardService.selectBoardList(startRow, endRow, searchKey, searchValue);

			// System.out.println("startRow//"+startRow);
			// System.out.println("endRow//"+endRow);

			// 총 페이지 : 총 게시글 / 10
			totalPage = totalBoardCount / NUM_OF_BOARD;
			if (totalBoardCount % NUM_OF_BOARD != 0) {
				totalPage++;
			}

			// 시작 페이지
			startPage = 1;
			if (startPage <= 0) {
				startPage = 1;
			}
			endPage = startPage + 9;

			if (endPage > totalPage) {
				endPage = totalPage;
			}

			while (pageNo > endPage) {
				startPage = startPage + 10;
				endPage = startPage + 9;

				if (endPage > totalPage) {
					endPage = totalPage;
				}

			}

			boolean isNowFirst = pageNo == 1 ? true : false; // 시작 페이지 (전체)
			boolean isNowFinal = pageNo == totalPage ? true : false; // 마지막 페이지 (전체)

			if (isNowFirst) {
				prePage = 1; // 이전 페이지 번호
			} else {
				prePage = (((pageNo - 1) < 1 ? 1 : (pageNo - 1))); // 이전 페이지 번호
			}

			if (isNowFinal) {
				nextPage = (totalPage); // 다음 페이지 번호
			} else {
				nextPage = (((pageNo + 1) > totalPage ? totalPage : (pageNo + 1))); // 다음 페이지 번호
			}

			// System.out.println("prePage//"+prePage);
			// System.out.println("nextPage//"+nextPage);
			// System.out.println("startPage//"+startPage);
			// System.out.println("endPage//"+endPage);
			// System.out.println("pageNo//"+pageNo);
			// System.out.println("-------------------------------------");
			System.out.println("보드리스트 화면입니다!!");
			BoardPage boardPage = new BoardPage(totalBoardCount, startPage, endPage, startRow, endRow, totalPage,
					prePage, nextPage, pageNo, boardList);
			mv.addObject("boardPage", boardPage);
			return mv;

		} else { // 게시글이 없는 경우
			BoardPage boardPage = new BoardPage(0, 0, 0, 0, 0, 0, 0, 0, 0, Collections.<Board> emptyList());
			mv.addObject("boardPage", boardPage);
			return mv;
		}

	}

	@RequestMapping(value = "read.do")
	public ModelAndView readBoard(int boardNo, boolean isHitCount, int page, String searchKey, String searchValue) {
		Board board = boardService.readBoardByBoardNo(boardNo, isHitCount);
		String content = board.getContent();
		content = content.replaceAll("\r\n", "<br>");
		System.out.println("글읽기 searchKey//" + searchKey + "//searchValue//" + searchValue + "//page//" + page);
		board.setContent(content);
		ModelAndView mv = new ModelAndView("read");
		if (searchKey != null && searchValue != null) {
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			mv.addObject("page", page);
		}
		mv.addObject("board", board);
		// mv.addObject("content", content);
		List<Comment> commentList = commentService.readCommentListByBoardNo(boardNo);
//		for(Comment c : commentList){
//			logger.debug("보드 컨드롤러 코멘트 : "+c);
//		}
		mv.addObject("commentList", commentList);
		return mv;
	}

	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	// 글쓰기 파일업로드실행
	@RequestMapping(value = "write.do")
	public String write(Board board, HttpServletRequest request, UploadFile uploadFile) {
		try {
			int boardNo = boardService.selectLastNo();
			// board.getContent().replaceAll("\n", "<br>");
			List<vo.File> files = uploadFile(uploadFile, request, boardNo);
			System.out.println("업로드파일/" + uploadFile);
			System.out.println("원글번호/" + boardNo);
			boardService.writeBoard(board);

			System.out.println(files);
			for (vo.File file : files) {
				fileServie.insertFile(file, boardNo);
			}
		} catch (UnknownHostException e) {
			System.out.println("글작성 에러 컨트롤러");
		}

		return "redirect:boardList.do?page=1";
	}

	// 파일 업로드
	public List<vo.File> uploadFile(UploadFile voUploadFile, HttpServletRequest request, int boardNo) {
		List<vo.File> voFileList = new ArrayList<>();
		List<MultipartFile> fileListFromClient = voUploadFile.getFileList(); // 클라이언트가 전송한 파일
		System.out.println("업로드 파일리스트//" + fileListFromClient);
		List<String> filenames = new ArrayList<>();

		if (fileListFromClient != null && fileListFromClient.size() > 0) {
			// 파일 전송받음.
			String path = request.getServletContext().getRealPath("uploaded_file");

			File dir = new File(path);
			if (dir.exists() == false) { // 해당 경로에 폴더가 없는 경우 폴더 생성
				dir.mkdirs();
			}

			vo.File voFile;
			for (MultipartFile multiPartFile : fileListFromClient) {

				if (multiPartFile.getSize() <= 0 || "".equals(multiPartFile.getName())) {
					continue;
				}

				String uniqueId = UUID.randomUUID().toString(); // 파일 중복 이름 처리
				String savedFileName = multiPartFile.getOriginalFilename() + "." + uniqueId;
				String savedPath = path + "/" + savedFileName; // 저장될 파일 경로
				filenames.add(multiPartFile.getOriginalFilename() + "." + uniqueId);

				File uploadedFile = new File(savedPath);

				try {
					multiPartFile.transferTo(uploadedFile); // 업로드 실행.

					voFile = new vo.File();
					voFile.setBoardNo(boardNo);
					voFile.setOriginalName(multiPartFile.getOriginalFilename());
					voFile.setSavedPath(savedPath);
					voFile.setFlag(1);
					voFileList.add(voFile);

					System.out.println("파일 업로드 완료");
					System.out.println("파일명:" + multiPartFile.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					System.out.println("upload fail!!!!!!! " + e);

				}

			}
		}

		return voFileList;

	}

	// 답글쓰기
	@RequestMapping(value = "replyForm.do")
	public ModelAndView writeReplyForm(int boardNo) {
		System.out.println(boardNo + "/boardNo");
		Board board = boardService.readBoardByBoardNo(boardNo, false);

		ModelAndView mv = new ModelAndView("reply_form");
		mv.addObject("boardNo", boardNo);
		mv.addObject("board", board);
		return mv;
	}

	@RequestMapping(value = "reply.do", method = RequestMethod.POST)
	public String reply(Board board, int boardNo, HttpServletRequest request, UploadFile uploadFile)
			throws UnknownHostException {
		// System.out.println("컨트롤러 board//" + board);
		// System.out.println("컨트롤러 보드넘버(원글)//" + boardNo);

		int replyBoardNo = boardService.selectLastNo();
		List<vo.File> voFileList = uploadFile(uploadFile, request, replyBoardNo);
		// System.out.println("업로드파일/" + uploadFile);
		// System.out.println("원글번호/" + replyBoardNo);

		for (vo.File voFile : voFileList) {
			fileServie.insertFile(voFile, replyBoardNo);
		}

		boardService.writeReply(board, boardNo);
		return "redirect:boardList.do?page=1";
	}

	@RequestMapping(value = "ajaxUpdateForm.do")
	public @ResponseBody Map<Object, Object> ajaxUpdateForm(int boardNo , HttpServletRequest request) {
		String isAjax = (String) request.getAttribute("result");
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		logger.debug("업데이트에서 에이젝스 값 ////////////////"+isAjax ); 
		if (!"E".equals(isAjax)) {
			Board board = boardService.readBoardByBoardNo(boardNo, false);
			map.put("boardNo",boardNo);
		} else if("E".equals(isAjax)){
			map.put("isAjax", "E");
			logger.debug("map ////////////////////////////   : "+ map);
		}
		return map;
		
	}
	
	@RequestMapping(value = "updateForm.do")
	public ModelAndView updateForm(int boardNo){
		Board board = boardService.readBoardByBoardNo(boardNo, false);
		ModelAndView mv = new ModelAndView("update_form");
		mv.addObject("board", board);
		return mv;
	}
	
	
	

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(Board updatedBoard, int boardNo, UploadFile voUploadFile, HttpServletRequest request,
			String[] deletedFileList) throws UnknownHostException {

		System.out.println("-----------------------------------");
		System.out.println("여긴 오나?");
		System.out.println("업데이트된 게시판// " + updatedBoard);
		System.out.println("새로 업로드 파일// " + voUploadFile.getFileList());
		System.out.println("httpRequest// " + request);
		System.out.println("삭제된 파일 // " + deletedFileList);
		System.out.println("-----------------------------------");

		if (deletedFileList != null) {
			// 파일 삭제 수정

			int fileNo;
			int deletedFile;
			for (String s : deletedFileList) {
				fileNo = (int) Integer.parseInt(s);
				vo.File voFile = fileServie.selectFileByFileNo(fileNo);
				deletedFile = fileServie.deleteFile(fileNo);
				System.out.println("수정: 삭제된 파일 업데이트 : " + voFile + "//업데이트 수행 결과 : " + deletedFile);
			}
		}

		List<vo.File> voFileList = uploadFile(voUploadFile, request, boardNo);
		boardService.updateBoard(updatedBoard, boardNo, deletedFileList);
		// 파일 업데이트
		for (vo.File voFile : voFileList) {
			fileServie.insertFile(voFile, boardNo);
		}

		return "redirect:boardList.do?page=1";
	}

	@RequestMapping(value = "delete.do")
	public String delete(int boardNo) throws UnknownHostException {
		Board updatedBoard = boardService.readBoardByBoardNo(boardNo, false);
		System.out.println("원래 비밀번호있는 사용자 비번//" + updatedBoard);
		boardService.deleteBoard(updatedBoard, boardNo);
		return "redirect:boardList.do?page=1";
	}

}
