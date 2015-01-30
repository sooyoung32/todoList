package kr.co.kware.boardpsy.board;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.co.kware.boardpsy.comment.Comment;
import kr.co.kware.boardpsy.comment.CommentService;
import kr.co.kware.boardpsy.file.FileService;
import kr.co.kware.boardpsy.file.MultipartFileList;
import kr.co.kware.boardpsy.util.BoardPage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardServiceImpl;
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private FileService fileServiceImpl;
	
	@Autowired
	private CommentService commentServiceImpl;

	private Logger logger = Logger.getLogger(BoardController.class);

	// ������ ó��
	public static final int NUM_OF_BOARD = 15;

	@RequestMapping(value = "boardList.do")
	public ModelAndView getBoardPage(@RequestParam(value = "page", defaultValue = "1") int page, String searchKey,
			String searchValue) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board_list");
	 
//		for (String xx : ctx.getBeanNamesForType(BoardService.class)) {
//			System.err.println(">>> : " + xx);
//		}
		int totalBoardCount;
		int startRow = 0;
		int endRow = 0;
		int startPage = 0;
		int endPage = 0;
		int totalPage = 0;
		int prePage = 0;
		int nextPage = 0;
		int pageNo = 0;
		List<Board> boardList;
		
		if (searchKey != null && searchValue != null) { // �˻������ ������
			totalBoardCount = boardServiceImpl.countTotalSearchResult(searchKey, searchValue);
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);

		} else {
			totalBoardCount = boardServiceImpl.countTotalBoardNumber();
		}

		if (totalBoardCount > 0) { // �Խñ��� �ִ� ���
			pageNo = page;

			// �Խñ� ���� 0-9, 10-19
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;
			// ����Ʈ �ޱ�
			boardList = boardServiceImpl.showBoardList(startRow, endRow, searchKey, searchValue); //TODO map���ι޾ƿ�...startRow endRow ������ִ°��� �Ѱ���.

			// System.out.println("startRow//"+startRow);
			// System.out.println("endRow//"+endRow);

			// �� ������ : �� �Խñ� / 10
			totalPage = totalBoardCount / NUM_OF_BOARD;
			if (totalBoardCount % NUM_OF_BOARD != 0) {
				totalPage++;
			}

			// ���� ������
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

			boolean isNowFirst = pageNo == 1 ? true : false; // ���� ������ (��ü)
			boolean isNowFinal = pageNo == totalPage ? true : false; // ������ ������ (��ü)

			if (isNowFirst) {
				prePage = 1; // ���� ������ ��ȣ
			} else {
				prePage = (((pageNo - 1) < 1 ? 1 : (pageNo - 1))); // ���� ������ ��ȣ
			}

			if (isNowFinal) {
				nextPage = (totalPage); // ���� ������ ��ȣ
			} else {
				nextPage = (((pageNo + 1) > totalPage ? totalPage : (pageNo + 1))); // ���� ������ ��ȣ
			}

			logger.debug("���帮��Ʈ ȭ���Դϴ�!!");
			BoardPage boardPage = new BoardPage(totalBoardCount, startPage, endPage, startRow, endRow, totalPage,
					prePage, nextPage, pageNo, boardList);
			mv.addObject("boardPage", boardPage);
			return mv;

		} else { // �Խñ��� ���� ���
			BoardPage boardPage = new BoardPage(0, 0, 0, 0, 0, 0, 0, 0, 0, Collections.<Board> emptyList());
			mv.addObject("boardPage", boardPage);
			return mv;
		}

	}

	@RequestMapping(value = "read.do")
	public ModelAndView readBoard(int boardNo, boolean isHitCount, int page, String searchKey, String searchValue) {
		Board board = boardServiceImpl.readBoardbyBoardNo(boardNo, true);

		String content = board.getContent();// teaxArea �ٹٲ� ó��
		content = content.replaceAll("\r\n", "<br>");
		board.setContent(content);

		ModelAndView mv = new ModelAndView("read");

		if (searchKey != null && searchValue != null) {
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			mv.addObject("page", page);
		}
		mv.addObject("board", board);
		logger.debug("�Խñ� "+ board);
		List<Comment> commentList = commentServiceImpl.readCommentListByBoardNo(boardNo);
		mv.addObject("commentList", commentList);

		return mv;
	}

	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	// �۾��� ���Ͼ��ε����
	@RequestMapping(value = "write.do", method =RequestMethod.POST)
	public String write(Board board, HttpServletRequest request, MultipartFileList multiPartFileList) {
		int boardNo = boardServiceImpl.getLastBoardNo();
		// board.getContent().replaceAll("\n\r", "<br>");
		List<kr.co.kware.boardpsy.file.File> files = fileUpload(multiPartFileList, request, boardNo);
		boardServiceImpl.writeBoard(board);

		System.out.println(files);
		for (kr.co.kware.boardpsy.file.File file : files) {
			fileServiceImpl.insertFile(file, boardNo);
		}

		return "redirect:boardList.do";
	}

	// ���� ���ε�
	public List<kr.co.kware.boardpsy.file.File> fileUpload(MultipartFileList multiPartFileList, HttpServletRequest request, int boardNo) {
		List<kr.co.kware.boardpsy.file.File> voFileList = new ArrayList<>();
		List<MultipartFile> fileListFromClient = multiPartFileList.getFileList(); // Ŭ���̾�Ʈ�� ������ ����
		System.out.println("���ε� ���ϸ���Ʈ//" + fileListFromClient);
		List<String> filenames = new ArrayList<>();

		if (fileListFromClient != null && fileListFromClient.size() > 0) {
			// ���� ���۹���.
			String path = request.getServletContext().getRealPath("uploaded_file");

			File dir = new File(path);
			if (dir.exists() == false) { // �ش� ��ο� ������ ���� ��� ���� ����
				dir.mkdirs();
			}

			kr.co.kware.boardpsy.file.File voFile;
			for (MultipartFile multiPartFile : fileListFromClient) {

				if (multiPartFile != null || "".equals(multiPartFile.getName())) {
					continue;
				}

				String uniqueId = UUID.randomUUID().toString(); // ���� �ߺ� �̸� ó��
				String savedFileName = multiPartFile.getOriginalFilename() + "." + uniqueId;
				String savedPath = path + "/" + savedFileName; // ����� ���� ���
				filenames.add(multiPartFile.getOriginalFilename() + "." + uniqueId);

				File uploadedFile = new File(savedPath); // ������ ���� ����.

				try {
					multiPartFile.transferTo(uploadedFile); // ���ε� ����.

					voFile = new kr.co.kware.boardpsy.file.File();
					voFile.setBoardNo(boardNo);
					voFile.setOriginalName(multiPartFile.getOriginalFilename());
					voFile.setSavedPath(savedPath);
					voFile.setFlag(1);
					voFileList.add(voFile);

					System.out.println("���� ���ε� �Ϸ�");
					System.out.println("���ϸ�:" + multiPartFile.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					System.out.println("upload fail!!!!!!! " + e);
					//TODO ���� notification.. ���� �˸�! 2��° ���ϸ� ���ε尡 �ȵǸ�?  	
				}
			}
		}

		return voFileList;

	}

	// ��۾���
	@RequestMapping(value = "replyForm.do")
	public ModelAndView writeReplyForm(int boardNo) {
		System.out.println(boardNo + "/boardNo");
		Board board = boardServiceImpl.readBoardbyBoardNo(boardNo, false);

		ModelAndView mv = new ModelAndView("reply_form");
		mv.addObject("boardNo", boardNo);
		mv.addObject("board", board);
		return mv;
	}

	@RequestMapping(value = "reply.do", method = RequestMethod.POST)
	public String reply(Board board, int boardNo, HttpServletRequest request, MultipartFileList uploadFile)
			throws UnknownHostException {

		int replyBoardNo = boardServiceImpl.getLastBoardNo();
		List<kr.co.kware.boardpsy.file.File> voFileList = fileUpload(uploadFile, request, replyBoardNo);

		for (kr.co.kware.boardpsy.file.File voFile : voFileList) {
			fileServiceImpl.insertFile(voFile, replyBoardNo);
		}

		boardServiceImpl.writeBoardReply(board, boardNo);
		return "redirect:boardList.do?page=1";
	}


	@RequestMapping(value = "updateForm.do")
	public ModelAndView updateForm(int boardNo) {
		Board board = boardServiceImpl.readBoardbyBoardNo(boardNo, false);
		ModelAndView mv = new ModelAndView("update_form");
		mv.addObject("board", board);
		return mv;
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(Board updatedBoard, int boardNo, MultipartFileList multipartFileList, HttpServletRequest request,
			String[] deletedFileList) throws UnknownHostException {

		logger.debug("-----------------------------------");
		logger.debug("������Ʈ�� �Խ���// " + updatedBoard);
		logger.debug("���� ���ε� ����// " + multipartFileList.getFileList());
		logger.debug("httpRequest// " + request);
		logger.debug("������ ���� // " + deletedFileList);
		logger.debug("-----------------------------------");

		if (deletedFileList != null) {
			// ���� ���� ����
			int fileNo;
			int deleteResult;
			for (String strFileNum : deletedFileList) {
				fileNo = (int) Integer.parseInt(strFileNum);
				kr.co.kware.boardpsy.file.File voFile = fileServiceImpl.getFileByFileNo(fileNo);
				deleteResult = fileServiceImpl.deleteFile(fileNo);
				logger.debug("����: ������ ���� ������Ʈ : " + voFile + "//������Ʈ ���� ��� : " + deleteResult);
			}
		}

		List<kr.co.kware.boardpsy.file.File> voFileList = fileUpload(multipartFileList, request, boardNo);
		boardServiceImpl.modifyBoard(updatedBoard, boardNo, deletedFileList);
		
		// ���� ������Ʈ
		for (kr.co.kware.boardpsy.file.File voFile : voFileList) {
			fileServiceImpl.insertFile(voFile, boardNo);
		}

		return "redirect:boardList.do?page=1";
	}

	@RequestMapping(value = "delete.do")
	public String delete(int boardNo) throws UnknownHostException {
		Board updatedBoard = boardServiceImpl.readBoardbyBoardNo(boardNo, false);
		boardServiceImpl.deleteBoard(updatedBoard, boardNo);
		return "redirect:boardList.do?page=1";
	}

}
