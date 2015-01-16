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

	private Boolean ajaxCheck(HttpServletRequest request) {
		String isAjax = (String) request.getAttribute("result");

		if (("E").equals(isAjax)) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "insertComment.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String writeComment(Comment comment, int boardNo, String email, HttpServletRequest request)
			throws UnknownHostException {
		logger.debug("�ڸ�Ʈ ��Ʈ�ѷ�");
		int commentNo = commentService.selectCommentLastNo();
		comment.setCommentNo(commentNo);
		
		
		if(ajaxCheck(request)){
			return "E";
		}
		
		int insert = commentService.writeComment(comment, boardNo, email);
		if (insert == 1) {
			return "C_WRITE_SUCCESS";
		} else if (insert == 0) {
			return  "C_WRITE_FAIL";
		}
		 return "";	
	}

	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo, HttpServletRequest request) throws UnknownHostException {

		if(ajaxCheck(request)){
			return "E";
		}

		logger.debug("���� �ڸ�Ʈ��ȣ//" + commentNo);
		if (commentService.deleteComment(commentNo) == 1) {
			return "C_DELETE_SUCCESS";
		} else {
			return "C_DELETE_FAIL";
		}
	}

	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content, HttpServletRequest request) throws UnknownHostException {

		if(ajaxCheck(request)){
			return "E";
		}

		logger.debug("�ڸ�Ʈ ��ȣ//" + commentNo + " ----- �ڸ�Ʈ ���� ���� //" + content);
		if (commentService.updateComment(content, commentNo) == 1) {
			return "C_UPDATE_SUCCESS";
		} else {
			return "C_UPDATE_FAIL";
		}

	}

}
