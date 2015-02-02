package kr.co.kware.board.article.controller;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.co.kware.board.article.service.ArticleService;


import kr.co.kware.board.article.vo.Article;
import kr.co.kware.board.comment.service.CommentService;
import kr.co.kware.board.comment.vo.Comment;
import kr.co.kware.board.file.service.FileService;
import kr.co.kware.board.file.vo.MultipartFileList;
import kr.co.kware.common.util.pages.Page;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleServiceImpl;
	
	@Autowired
	private FileService fileServiceImpl;
	
	@Autowired
	private CommentService commentServiceImpl;

	private Logger logger = Logger.getLogger(ArticleController.class);

	// ������ ó��
	public static final int NUM_OF_BOARD = 15;

	@RequestMapping(value = "articleList.do")
	public ModelAndView getArticlePage(@RequestParam(value = "page", defaultValue = "1") int page, String searchKey,
			String searchValue) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("article_list");
	 
//		for (String xx : ctx.getBeanNamesForType(BoardService.class)) {
//			System.err.println(">>> : " + xx);
//		}
		int totalArticleCount;
		int startRow = 0;
		int endRow = 0;
		int startPage = 0;
		int endPage = 0;
		int totalPage = 0;
		int prePage = 0;
		int nextPage = 0;
		int pageNo = 0;
		List<Article> articleList;
		
		if (searchKey != null && searchValue != null) { // �˻������ ������
			totalArticleCount = articleServiceImpl.countTotalSearchResult(searchKey, searchValue);
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);

		} else {
			totalArticleCount = articleServiceImpl.countTotalArticleNumber();
		}

		if (totalArticleCount > 0) { // �Խñ��� �ִ� ���
			pageNo = page;

			// �Խñ� ���� 0-9, 10-19
			startRow = (page - 1) * NUM_OF_BOARD;
			endRow = startRow + NUM_OF_BOARD - 1;
			// ����Ʈ �ޱ�
			articleList = articleServiceImpl.showArticleList(startRow, endRow, searchKey, searchValue); //TODO map���ι޾ƿ�...startRow endRow ������ִ°��� �Ѱ���.

			// System.out.println("startRow//"+startRow);
			// System.out.println("endRow//"+endRow);

			// �� ������ : �� �Խñ� / 10
			totalPage = totalArticleCount / NUM_OF_BOARD;
			if (totalArticleCount % NUM_OF_BOARD != 0) {
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
			Page<Article> articlePage = new Page<Article>(totalArticleCount, startPage, endPage, startRow, endRow, totalPage,
					prePage, nextPage, pageNo, articleList);
			mv.addObject("articlePage", articlePage);
			return mv;

		} else { // �Խñ��� ���� ���
			Page<Article> articlePage = new Page(0, 0, 0, 0, 0, 0, 0, 0, 0, Collections.<Article> emptyList());
			mv.addObject("articlePage", articlePage);
			return mv;
		}

	}

	@RequestMapping(value = "read.do")
	public ModelAndView readArticle(int articleNo, boolean isHitCount, int page, String searchKey, String searchValue) {
		Article article = articleServiceImpl.readArticlebyArticleNo(articleNo, true);

		String content = article.getContent();// teaxArea �ٹٲ� ó��
		content = content.replaceAll("\r\n", "<br>");
		article.setContent(content);

		ModelAndView mv = new ModelAndView("read");

		if (searchKey != null && searchValue != null) {
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			mv.addObject("page", page);
		}
		mv.addObject("article", article);
		logger.debug("�Խñ� "+ article);
		List<Comment> commentList = commentServiceImpl.readCommentListByBoardNo(articleNo);
		mv.addObject("commentList", commentList);

		return mv;
	}

	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	// �۾��� ���Ͼ��ε����
	@RequestMapping(value = "write.do", method =RequestMethod.POST)
	public String write(Article article, HttpServletRequest request, MultipartFileList multiPartFileList) {
		int articleNo = articleServiceImpl.getLastArticleNo();
		// article.getContent().replaceAll("\n\r", "<br>");
		List<kr.co.kware.board.file.vo.File> files = fileUpload(multiPartFileList, request, articleNo);
		articleServiceImpl.writeArticle(article);

		System.out.println(files);
		for (kr.co.kware.board.file.vo.File file : files) {
			fileServiceImpl.insertFile(file, articleNo);
		}

		return "redirect:articleList.do";
	}

	// ���� ���ε�
	public List<kr.co.kware.board.file.vo.File> fileUpload(MultipartFileList multiPartFileList, HttpServletRequest request, int articleNo) {
		List<kr.co.kware.board.file.vo.File> voFileList = new ArrayList<>();
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

			kr.co.kware.board.file.vo.File voFile;
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

					voFile = new kr.co.kware.board.file.vo.File();
					voFile.setBoardNo(articleNo);
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
	public ModelAndView writeReplyForm(int articleNo) {
		System.out.println(articleNo + "/articleNo");
		Article article = articleServiceImpl.readArticlebyArticleNo(articleNo, false);

		ModelAndView mv = new ModelAndView("reply_form");
		mv.addObject("articleNo", articleNo);
		mv.addObject("article", article);
		return mv;
	}

	@RequestMapping(value = "reply.do", method = RequestMethod.POST)
	public String reply(Article article, int articleNo, HttpServletRequest request, MultipartFileList uploadFile)
			throws UnknownHostException {

		int replyArticleNo = articleServiceImpl.getLastArticleNo();
		List<kr.co.kware.board.file.vo.File> voFileList = fileUpload(uploadFile, request, replyArticleNo);

		for (kr.co.kware.board.file.vo.File voFile : voFileList) {
			fileServiceImpl.insertFile(voFile, replyArticleNo);
		}

		articleServiceImpl.writeArticleReply(article, articleNo);
		return "redirect:articleList.do?page=1";
	}


	@RequestMapping(value = "updateForm.do")
	public ModelAndView updateForm(int articleNo) {
		Article article = articleServiceImpl.readArticlebyArticleNo(articleNo, false);
		ModelAndView mv = new ModelAndView("update_form");
		mv.addObject("article", article);
		return mv;
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(Article updatedArticle, int articleNo, MultipartFileList multipartFileList, HttpServletRequest request,
			String[] deletedFileList) throws UnknownHostException {

		logger.debug("-----------------------------------");
		logger.debug("������Ʈ�� �Խ���// " + updatedArticle);
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
				kr.co.kware.board.file.vo.File voFile = fileServiceImpl.getFileByFileNo(fileNo);
				deleteResult = fileServiceImpl.deleteFile(fileNo);
				logger.debug("����: ������ ���� ������Ʈ : " + voFile + "//������Ʈ ���� ��� : " + deleteResult);
			}
		}

		List<kr.co.kware.board.file.vo.File> voFileList = fileUpload(multipartFileList, request, articleNo);
		articleServiceImpl.modifyArticle(updatedArticle, articleNo, deletedFileList);
		
		// ���� ������Ʈ
		for (kr.co.kware.board.file.vo.File voFile : voFileList) {
			fileServiceImpl.insertFile(voFile, articleNo);
		}

		return "redirect:articleList.do?page=1";
	}

	@RequestMapping(value = "delete.do")
	public String delete(int articleNo) throws UnknownHostException {
		Article updatedArticle = articleServiceImpl.readArticlebyArticleNo(articleNo, false);
		articleServiceImpl.deleteArticle(updatedArticle, articleNo);
		return "redirect:articleList.do?page=1";
	}

}
