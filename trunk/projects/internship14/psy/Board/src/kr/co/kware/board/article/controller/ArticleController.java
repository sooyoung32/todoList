package kr.co.kware.board.article.controller;

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

import kr.co.kware.board.article.service.ArticleService;
import kr.co.kware.board.article.vo.Article;
import kr.co.kware.board.comment.service.CommentService;
import kr.co.kware.board.comment.vo.Comment;
import kr.co.kware.board.file.service.FileService;
import kr.co.kware.board.file.vo.MultipartFileList;
import kr.co.kware.common.dbcommon.DeletionStatus;
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

	// 페이지 처리

	@RequestMapping(value = "articleList.do")
	public ModelAndView getArticlePage(@RequestParam(value = "page", defaultValue = "1") int page, String searchKey,
			String searchValue, HttpServletRequest request) {
		
		String currentPage = request.getParameter("currentPage");
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("article_list");

		int totalArticleCount = 0;
		List<Article> articleList;

		Page<Article> articlePage = new Page<Article>();
		articlePage.setCurrentPageNo(page);
//		articlePage.setPageSize(15);
				
		if (searchKey != null && searchValue != null) { // 검색결과가 있을때
			totalArticleCount = articleServiceImpl.countTotalSearchResult(searchKey, searchValue);
			articlePage.setTotalRecodeCount(totalArticleCount);
			
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);

		} else {
			totalArticleCount = articleServiceImpl.countTotalArticleNumber();
			articlePage.setTotalRecodeCount(totalArticleCount);
		}
		
		int startRow = (page - 1) * articlePage.getPageSize();
		int endRow = articlePage.getPageSize();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		
		articleList = articleServiceImpl.showArticleList(map);
		
		articlePage.setStartRow(startRow);
		articlePage.setEndRow(endRow);
		articlePage.setTotalRecodeCount(totalArticleCount);
		articlePage.setDataList(articleList);
		
		request.setAttribute("dataList", articleList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalRecordCount",totalArticleCount);
        request.setAttribute("recordsPerPage", 15);
		
		mv.addObject("articlePage",articlePage);
		return mv;

	}

	@RequestMapping(value = "read.do")
	public ModelAndView readArticle(int articleNo, boolean isHitCount, int page, String searchKey, String searchValue) {
		Article article = articleServiceImpl.readArticlebyArticleNo(articleNo, true);

		String content = article.getContent();// teaxArea 줄바꿈 처리
		content = content.replaceAll("\r\n", "<br>");
		article.setContent(content);

		ModelAndView mv = new ModelAndView("read");

		if (searchKey != null && searchValue != null) {
			mv.addObject("searchKey", searchKey);
			mv.addObject("searchValue", searchValue);
			mv.addObject("page", page);
		}
		mv.addObject("article", article);
		logger.debug("게시글 " + article);
		List<Comment> commentList = commentServiceImpl.readCommentListByArticleNo(articleNo);
		mv.addObject("commentList", commentList);

		return mv;
	}

	@RequestMapping(value = "writeForm.do")
	public String writeForm() {
		return "write_form";
	}

	// 글쓰기 파일업로드실행
	@RequestMapping(value = "write.do", method = RequestMethod.POST)
	public String write(Article article, HttpServletRequest request, MultipartFileList multipartFileList) {
		int articleNo = articleServiceImpl.getLastArticleNo();
		// article.getContent().replaceAll("\n\r", "<br>");
		List<kr.co.kware.board.file.vo.File> files = fileUpload(multipartFileList, request, articleNo);
		articleServiceImpl.writeArticle(article);

		logger.debug("글쓰기 컨드롤러 업로든된 파일: " + files);
		for (kr.co.kware.board.file.vo.File file : files) {
			fileServiceImpl.insertFile(file, articleNo);
		}

		return "redirect:articleList.do";
	}

	// 파일 업로드
	public List<kr.co.kware.board.file.vo.File> fileUpload(MultipartFileList multipartFileList,
			HttpServletRequest request, int articleNo) {
		List<kr.co.kware.board.file.vo.File> voFileList = new ArrayList<>();
		List<MultipartFile> fileListFromClient = multipartFileList.getFileList(); // 클라이언트가 전송한 파일
		System.err.println("업로드 파일리스트//" + fileListFromClient);
		System.err.println("멀티파트파일리스트 : " + multipartFileList + " 글번호 :" + articleNo);
		List<String> filenames = new ArrayList<>();

		if (fileListFromClient != null && fileListFromClient.size() > 0) {
			// 파일 전송받음.
			String path = request.getServletContext().getRealPath("uploaded_file");

			File dir = new File(path);
			if (dir.exists() == false) { // 해당 경로에 폴더가 없는 경우 폴더 생성
				dir.mkdirs();
			}
			System.err.println("여기 파일 업로드 : " + fileListFromClient);
			kr.co.kware.board.file.vo.File voFile;

			for (MultipartFile multiPartFile : fileListFromClient) {

				// if (multiPartFile != null || "".equals(multiPartFile.getName())) {
				// continue;
				// } //TODO 이거...넣으면 파일 업로드가 안되지만 에러도 안난다. 어떻게 처리하지.. 방어적 프로그램?

				String uniqueId = UUID.randomUUID().toString(); // 파일 중복 이름 처리
				String savedFileName = multiPartFile.getOriginalFilename() + "." + uniqueId;
				String savedPath = path + "/" + savedFileName; // 저장될 파일 경로
				filenames.add(multiPartFile.getOriginalFilename() + "." + uniqueId);

				File uploadedFile = new File(savedPath); // 서버에 파일 저장.
				System.err.println("파일 업로드? 들어와 : " + uploadedFile);
				try {
					multiPartFile.transferTo(uploadedFile); // 업로드 실행.

					voFile = new kr.co.kware.board.file.vo.File();
					voFile.setArticleNo(articleNo);
					voFile.setOriginalName(multiPartFile.getOriginalFilename());
					voFile.setSavedPath(savedPath);
					voFile.setDeletionStatus(DeletionStatus.PRESENT);
					voFileList.add(voFile);

					System.out.println("파일 업로드 완료");
					System.out.println("파일명:" + multiPartFile.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					System.out.println("upload fail!!!!!!! " + e);
					// TODO 파일 notification.. 에러 알림! 2번째 파일만 업로드가 안되면?
				}
			}
		}

		return voFileList;

	}

	// 답글쓰기
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
	public String update(Article updatedArticle, int articleNo, MultipartFileList multipartFileList,
			HttpServletRequest request, String[] deletedFileList) throws UnknownHostException {

		logger.debug("-----------------------------------");
		logger.debug("업데이트된 게시판// " + updatedArticle);
		logger.debug("새로 업로드 파일// " + multipartFileList.getFileList());
		logger.debug("httpRequest// " + request);
		logger.debug("삭제된 파일 // " + deletedFileList);
		logger.debug("-----------------------------------");

		if (deletedFileList != null) {
			// 파일 삭제 수정
			int fileNo;
			int deleteResult;
			for (String strFileNum : deletedFileList) {
				fileNo = (int) Integer.parseInt(strFileNum);
				kr.co.kware.board.file.vo.File voFile = fileServiceImpl.getFileByFileNo(fileNo);
				deleteResult = fileServiceImpl.deleteFile(fileNo);
				logger.debug("수정: 삭제된 파일 업데이트 : " + voFile + "//업데이트 수행 결과 : " + deleteResult);
			}
		}

		List<kr.co.kware.board.file.vo.File> voFileList = fileUpload(multipartFileList, request, articleNo);
		articleServiceImpl.modifyArticle(updatedArticle, articleNo, deletedFileList);

		// 파일 업데이트
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
