package controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CommentService;
import vo.Comment;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;

	private Logger logger = Logger.getLogger(CommentController.class);

	@RequestMapping(value = "insertComment.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String writeComment(Comment comment, int boardNo, String email, HttpServletRequest request)
			throws UnknownHostException {
		logger.debug("코멘트 컨트롤러");
		int commentNo = commentService.selectCommentLastNo();
		comment.setCommentNo(commentNo);
		String result = null;
		String isAjax = (String) request.getAttribute("result");

		if (("E").equals(isAjax)) {
			result = "E";
			return result;
		}

		int insert = commentService.writeComment(comment, boardNo, email);
		
		if (insert == 1) {
			result = "C_WRITE_SUCCESS";
			System.out.println("result/" + result);
			return result;
		} else if (insert == 0) {
			result = "C_WRITE_FAIL";
			System.out.println("result/" + result);
			return result;
		}
		return result;

	}

	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo, HttpServletRequest request) throws UnknownHostException {

		String isAjax = (String) request.getAttribute("result");
		System.out.println("에이젝스 //" + isAjax);
		if (("E").equals(isAjax)) {
			return "E";
		}

		System.out.println("삭제 코멘트번호//" + commentNo);
		if (commentService.deleteComment(commentNo) == 1) {
			return "C_DELETE_SUCCESS";
		} else {
			return "C_DELETE_FAIL";
		}
	}

	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content, HttpServletRequest request) throws UnknownHostException {

		String isAjax = (String) request.getAttribute("result");
		System.out.println("에이젝스 //" + isAjax);
		if (("E").equals(isAjax)) {
			return "E";
		}

		System.out.println("코멘트 번호//" + commentNo + " ----- 코멘트 수정 내용 //" + content);
		if (commentService.updateComment(content, commentNo) == 1) {
			return "C_UPDATE_SUCCESS";
		} else {
			return "C_UPDATE_FAIL";
		}

	}

}
