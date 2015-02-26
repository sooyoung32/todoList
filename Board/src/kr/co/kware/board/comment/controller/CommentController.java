package kr.co.kware.board.comment.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import kr.co.kware.board.comment.service.CommentService;
import kr.co.kware.board.comment.vo.Comment;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentServiceImpl;

	private Logger logger = Logger.getLogger(CommentController.class);

	 
	private Boolean isAjax(HttpServletRequest request) {
		String isAjax = (String) request.getAttribute("result");
		if (("E").equals(isAjax)) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "insertComment.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String writeComment(Comment comment, int articleNo, String email, HttpServletRequest request)
			throws UnknownHostException {
		
		logger.debug("코멘트 컨트롤러");
		int commentNo = commentServiceImpl.getLastCommnetNo();
		comment.setCommentNo(commentNo);
		String ip = getClientIP(request);
		comment.setWritingIP(ip);
		
		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.writeComment(comment, articleNo, email) == 1){
			return "C_WRITE_SUCCESS";
		} else {
			return  "C_WRITE_FAIL";
		}
	}

	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo, HttpServletRequest request) throws UnknownHostException {
		
		String ip = getClientIP(request);
		logger.debug("삭제 코멘트번호//" + commentNo);
		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.deleteComment(commentNo, ip) == 1) {
			return "C_DELETE_SUCCESS";
		} else {
			return "C_DELETE_FAIL";
		}
	}

	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content, HttpServletRequest request) throws UnknownHostException {
		
		String ip = getClientIP(request);
		
		
		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.modifyComment(content, commentNo, ip) == 1) {
			return "C_UPDATE_SUCCESS";
		} else {
			return "C_UPDATE_FAIL";
		}

	}
	
	public String getClientIP(HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		
		return ip;

	}
}
