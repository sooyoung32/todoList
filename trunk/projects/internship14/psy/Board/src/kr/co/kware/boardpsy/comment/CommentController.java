package kr.co.kware.boardpsy.comment;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

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
	public String writeComment(Comment comment, int boardNo, String email, HttpServletRequest request)
			throws UnknownHostException {
		
		logger.debug("코멘트 컨트롤러");
		int commentNo = commentServiceImpl.getLastCommnetNo();
		comment.setCommentNo(commentNo);
		
		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.writeComment(comment, boardNo, email) == 1){
			return "C_WRITE_SUCCESS";
		} else {
			return  "C_WRITE_FAIL";
		}
	}

	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo, HttpServletRequest request) throws UnknownHostException {

		logger.debug("삭제 코멘트번호//" + commentNo);
		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.deleteComment(commentNo) == 1) {
			return "C_DELETE_SUCCESS";
		} else {
			return "C_DELETE_FAIL";
		}
	}

	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content, HttpServletRequest request) throws UnknownHostException {

		if(isAjax(request)){
			return "E";
		}else if (commentServiceImpl.modifyComment(content, commentNo) == 1) {
			return "C_UPDATE_SUCCESS";
		} else {
			return "C_UPDATE_FAIL";
		}

	}

}
