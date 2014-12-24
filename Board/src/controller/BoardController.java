package controller;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import service.BoardService;
import service.FileService;
import vo.Board;
import vo.BoardPage;
import vo.UploadFile;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private FileService fileServie;

	// 페이지 처리
	public static final int NUM_OF_BOARD = 15;

	@RequestMapping(value = "boardList.do")
	public ModelAndView getBoardPage(@RequestParam(value = "page", defaultValue = "1") int page) {
		ModelAndView mv = new ModelAndView("board_list");

		int totalBoardCount = boardService.selectBoardCount();
		int startPage = 0;
		int endPage = 0;
		int startRow = 0;
		int endRow = 0;
		int totalPage = 0;
		List<Board> boardList;

		if (totalBoardCount > 0) {
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;
			boardList = boardService.selectBoardList(startRow, endRow);

			totalPage = totalBoardCount / NUM_OF_BOARD;
			if (totalBoardCount % NUM_OF_BOARD != 0) {
				totalPage++;
			}

			startPage = page - 10;
			if (startPage <= 0) {
				startPage = 1;
			}

			endPage = page + 10;
			if (endPage > totalPage) {
				endPage = totalPage;
			}

			BoardPage boardPage = new BoardPage(boardList, startPage, endPage, startRow, endRow, totalPage);
			mv.addObject("boardPage", boardPage);
			return mv;

		} else {
			BoardPage boardPage = new BoardPage(Collections.<Board> emptyList(), 0, 0, 0, 0, 0);
			mv.addObject("boardPage", boardPage);
			return mv;
		}
	}

	@RequestMapping(value = "read.do", method = RequestMethod.GET)
	public ModelAndView readBoard(int boardNo, boolean isHitCount) {
		Board board = boardService.selectBoardByBoardNo(boardNo, isHitCount);
		ModelAndView mv = new ModelAndView("read");
		mv.addObject("board", board);
		return mv;
	}

	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	@RequestMapping(value = "write.do", method = RequestMethod.POST)
	public String write(Board board, HttpServletRequest request, UploadFile uploadFile) {
		try {
			int boardNo = boardService.selectLastNo();
			List<vo.File> files = uploadFile(uploadFile, request, boardNo);
			System.out.println("업로드파일/"+uploadFile);
			System.out.println("원글번호/"+boardNo);
			boardService.insertBoard(board);

			System.out.println(files);
			for (vo.File file : files) {
				fileServie.insertFile(file, boardNo);
			}
		} catch (UnknownHostException e) {
			System.out.println("글작성 에러 컨트롤러");
		}

		return "redirect:boardList.do?page=1";
	}

	public List<vo.File> uploadFile(UploadFile uploadFile, HttpServletRequest request, int boardNo) {
		List<vo.File> files = new ArrayList<>();
		List<MultipartFile> fileList = uploadFile.getFileList(); // 클라이언트가 전송한 파일
		System.err.println(">>" + fileList);

		List<String> filenames = new ArrayList<>();

		if (fileList != null && fileList.size() > 0) {
			// 파일 전송받음.
			String path = request.getServletContext().getRealPath("uploaded_file");

			File dir = new File(path);
			if (dir.exists() == false) { // 해당 경로에 폴더가 없는 경우 폴더 생성
				dir.mkdirs();
			}

			vo.File voFile;
			for (MultipartFile file : fileList) {
				File uploadedFile = new File(path + "/" + file.getOriginalFilename());
				filenames.add(file.getOriginalFilename());

				try {
					file.transferTo(uploadedFile); // 업로드 실행.

					voFile = new vo.File();
					voFile.setBoardNo(boardNo);
					voFile.setOriginalName(file.getOriginalFilename());
					voFile.setSavedPath(path + "/" + file.getOriginalFilename());

					files.add(voFile);

					System.out.println("파일 업로드 완료");
					System.out.println("파일명:" + file.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					System.out.println("upload fail" + e);

				}

			}
		}

		return files;

	}

	@RequestMapping(value = "replyForm.do")
	public ModelAndView writeReplyForm(int boardNo) {
		System.out.println(boardNo + "/boardNo");
		Board board = boardService.selectBoardByBoardNo(boardNo, false);

		ModelAndView mv = new ModelAndView("reply_form");
		mv.addObject("boardNo", boardNo);
		mv.addObject("board", board);
		return mv;
	}
	
	@RequestMapping(value = "reply.do", method = RequestMethod.POST)
	public String reply(Board board, int boardNo, HttpServletRequest request,UploadFile uploadFile ) throws UnknownHostException {
		System.out.println("컨트롤러 board//"+board);
		System.out.println("컨트롤러 보드넘버(원글)//"+boardNo);
		
		int replyBoardNo = boardService.selectLastNo();
		List<vo.File> files = uploadFile(uploadFile, request,replyBoardNo);
		System.out.println("업로드파일/"+uploadFile);
		System.out.println("원글번호/"+replyBoardNo);
		
		for (vo.File file : files) {
			fileServie.insertFile(file, replyBoardNo);
		}
		
		boardService.insertReply(board, boardNo);
		return "redirect:boardList.do?page=1";
	}

}
