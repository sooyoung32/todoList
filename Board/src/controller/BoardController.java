package controller;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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
	

	// ������ ó��
	public static final int NUM_OF_BOARD = 15;

	@RequestMapping(value = "boardList.do")
	public ModelAndView getBoardPage(@RequestParam(value = "page", defaultValue = "1") int page, String searchKey, String searchValue) {
		ModelAndView mv = new ModelAndView("board_list");
		
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

		
		if(searchKey != null && searchValue != null){
			totalBoardCount = boardService.searchBoardCount(searchKey, searchValue);
			System.out.println("����Ʈ searchKey//"+searchKey +"//searchValue//"+searchValue);
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			
		}else{
			totalBoardCount = boardService.selectBoardCount();
			System.out.println("�˻�X//"+totalBoardCount);
		}
		
		System.out.println("totCnt =" + totalBoardCount);
		if (totalBoardCount > 0) { //�Խñ��� �ִ� ��� 
			
			pageNo = (page-1)+1;
			
			//�Խñ� ���� 0-9, 10-19 
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;
			
			//����Ʈ �ޱ� 
			boardList = boardService.selectBoardList(startRow, endRow, searchKey, searchValue);
			
			System.out.println("startRow//"+startRow);
			System.out.println("endRow//"+endRow);
		
			//�� ������ : �� �Խñ� / 10 
			totalPage = totalBoardCount / NUM_OF_BOARD;
			if (totalBoardCount % NUM_OF_BOARD != 0) {
				totalPage++;
			}
			
			System.out.println("totalPage//"+totalPage);

			boolean isNowFirst = pageNo == 1 ? true : false; // ���� ������ (��ü)
			boolean isNowFinal = pageNo == totalPage ? true : false; // ������ ������ (��ü)
			
			//���� ������ 
			
			startPage = 1;
			if (startPage <= 0) {
				startPage = 1;
			}
			endPage = startPage + 9;
			
			if(endPage > totalPage){
				endPage=totalPage;
			}

			while(pageNo > endPage){
				startPage = startPage+10;
				endPage = startPage + 9;
				
				if (endPage > totalPage) {
					endPage = totalPage;
				}
				
			}
			
			
			

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
		        
		    System.out.println("prePage//"+prePage);
			System.out.println("nextPage//"+nextPage);
			System.out.println("startPage//"+startPage);
			System.out.println("endPage//"+endPage);
			System.out.println("pageNo//"+pageNo);
			System.out.println("-------------------------------------");
			BoardPage boardPage = new BoardPage(totalBoardCount, startPage, endPage, startRow, endRow, totalPage, prePage, nextPage, pageNo, boardList);
			mv.addObject("boardPage", boardPage);
			return mv;

		
		}else { //�Խñ��� ���� ��� 
			BoardPage boardPage = new BoardPage(0, 0, 0, 0, 0, 0,0,0,0,Collections.<Board> emptyList());
			mv.addObject("boardPage", boardPage);
			return mv;
			}
		
	}

	@RequestMapping(value = "read.do")
	public ModelAndView readBoard(int boardNo, boolean isHitCount, int page, String searchKey, String searchValue) {
		Board board = boardService.selectBoardByBoardNo(boardNo, isHitCount);
		
		ModelAndView mv = new ModelAndView("read");
		System.out.println("���б� searchKey//"+searchKey +"//searchValue//"+searchValue +"//page//"+page);
	
		if(searchKey != null && searchValue != null){
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			mv.addObject("page", page);
			
		}
		
		mv.addObject("board", board);
		List<Comment> commentList = commentService.selectCommentListByBoardNo(boardNo);
		mv.addObject("commentList", commentList);
		return mv;
	}

	
	
	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	
	//�۾��� ���Ͼ��ε����
	@RequestMapping(value = "write.do", method = RequestMethod.POST)
	public String write(Board board, HttpServletRequest request, UploadFile uploadFile) {
		try {
			int boardNo = boardService.selectLastNo();
			List<vo.File> files = uploadFile(uploadFile, request, boardNo);
			System.out.println("���ε�����/" + uploadFile);
			System.out.println("���۹�ȣ/" + boardNo);
			boardService.insertBoard(board);

			System.out.println(files);
			for (vo.File file : files) {
				fileServie.insertFile(file, boardNo);
			}
		} catch (UnknownHostException e) {
			System.out.println("���ۼ� ���� ��Ʈ�ѷ�");
		}

		return "redirect:boardList.do?page=1";
	}

	//���� ���ε�
	public List<vo.File> uploadFile(UploadFile uploadFile, HttpServletRequest request, int boardNo) {
		List<vo.File> files = new ArrayList<>();
		List<MultipartFile> fileList = uploadFile.getFileList(); // Ŭ���̾�Ʈ�� ������ ����
		System.out.println("���ε� ���ϸ���Ʈ//" + fileList);
		List<String> filenames = new ArrayList<>();

		if (fileList != null && fileList.size() > 0) {
			// ���� ���۹���.
			String path = request.getServletContext().getRealPath("uploaded_file");

			File dir = new File(path);
			if (dir.exists() == false) { // �ش� ��ο� ������ ���� ��� ���� ����
				dir.mkdirs();
			}

			vo.File voFile;
			for (MultipartFile file : fileList) {
			
				if ( file.getSize() <= 0 || "".equals(file.getName()) ) {
					continue;
				}
				
				String genId = UUID.randomUUID().toString(); //���� �ߺ� �̸� ó��
				String originalFileName = file.getOriginalFilename(); //���� ���� �̸�
				String savedFileName = genId+"."+file.getOriginalFilename();
				String savedPath = path +"/"+savedFileName; //����� ���� ���
						
				
				File uploadedFile = new File(savedPath);
				filenames.add(genId+"."+file.getOriginalFilename());
				

				try {
					file.transferTo(uploadedFile); // ���ε� ����.

					voFile = new vo.File();
					voFile.setBoardNo(boardNo);
					voFile.setOriginalName(file.getOriginalFilename());
					voFile.setSavedPath(savedPath);
					voFile.setFlag(1);
					files.add(voFile);

					System.out.println("���� ���ε� �Ϸ�");
					System.out.println("���ϸ�:" + file.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					System.out.println("upload fail" + e);

				}

			}
		}

		return files;

	}

	//��۾���
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
	public String reply(Board board, int boardNo, HttpServletRequest request, UploadFile uploadFile)
			throws UnknownHostException {
//		System.out.println("��Ʈ�ѷ� board//" + board);
//		System.out.println("��Ʈ�ѷ� ����ѹ�(����)//" + boardNo);

		int replyBoardNo = boardService.selectLastNo();
		List<vo.File> files = uploadFile(uploadFile, request, replyBoardNo);
//		System.out.println("���ε�����/" + uploadFile);
//		System.out.println("���۹�ȣ/" + replyBoardNo);

		for (vo.File file : files) {
			fileServie.insertFile(file, replyBoardNo);
		}

		boardService.insertReply(board, boardNo);
		return "redirect:boardList.do?page=1";
	}

	@RequestMapping(value = "updateForm.do", method = RequestMethod.GET)
	public ModelAndView updateForm(int boardNo) {
		Board board = boardService.selectBoardByBoardNo(boardNo, false);
		ModelAndView mv = new ModelAndView("update_form");
		mv.addObject("board", board);
		return mv;
	}

	@RequestMapping(value = "update.do",  method = {RequestMethod.POST,RequestMethod.GET})	
	public String update(Board updatedBoard, int boardNo, UploadFile uploadFile, HttpServletRequest request,String[] deletedFileList) throws UnknownHostException{
		
	
		if(deletedFileList != null ){	
		//���� ���� ����
			for(String s : deletedFileList){
				int	fileNo = (int) Integer.parseInt(s); 
				vo.File file = fileServie.selectFileByFileNo(fileNo);
				int updqtedFile = fileServie.deleteFile(fileNo);
			}
		}
		
		
		List<vo.File> files = uploadFile(uploadFile, request, boardNo);
		boardService.updateBoard(updatedBoard, boardNo, deletedFileList);
		//���� ������Ʈ
		for (vo.File file : files) {
			fileServie.insertFile(file, boardNo);
		}
		return "redirect:boardList.do?page=1";
	}
	
	@RequestMapping(value="deleteForm.do", method = RequestMethod.GET)
	public ModelAndView deleteForm(int boardNo){
		System.out.println("�۹�ȣ//"+boardNo);
		Board board = boardService.selectBoardByBoardNo(boardNo, false);
		ModelAndView mv = new ModelAndView("delete_form");
		mv.addObject("board", board);
		return mv; 
	}
	
	@RequestMapping(value="delete.do", method = RequestMethod.POST)
	public String delete(int boardNo, String password) throws UnknownHostException{
		Board updatedBoard = boardService.selectBoardByBoardNo(boardNo, false);
		System.out.println("���� ��й�ȣ�ִ� ����� ���//"+updatedBoard);
		if(updatedBoard.getWriter().getPassword().equals(password)){
//			System.out.println("����ں��//"+updatedBoard.getWriter().getPassword());
//			System.out.println("�Է��Ѻ��//"+password);
			boardService.deleteBoard(updatedBoard, boardNo);
			return "redirect:boardList.do?page=1";
		}else{
			return "delete_fail";
		}
	}
	
}
