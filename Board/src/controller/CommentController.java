package controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

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

	
	@RequestMapping(value = "insertComment.do" ,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String insertComment(Comment comment, int boardNo, String email, HttpServletRequest request) throws UnknownHostException {
		System.out.println("코멘트 컨트롤러");
		int commentNo = commentService.selectCommentLastNo();
		comment.setCommentNo(commentNo);
//		System.out.println("comment객체/" + comment);
//		System.out.println("boardNo/" + boardNo);
//		System.out.println("코멘트 email/ " + email);
//		System.out.println("코멘트 commentNo/ " + commentNo);
		
		String result = null;
		String isAjax = (String) request.getAttribute("result");
		
		System.out.println("에이젝스 //"+isAjax);
//		boolean isAjax = "Y".equals(request.getParameter("ajaxYn"));
//		
		if(("E").equals(isAjax)){
			result = "E";
			return result;
		}
		
		int insert = commentService.insertComment(comment, boardNo, email);
		if (insert == 1) {
			result = "Y";
			System.out.println("result/" + result);
			return result;
		} else if(insert == 0){
			result = "N";
			System.out.println("result/" + result);
			return result;
		} 
		return result;

	}
	
	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo) throws UnknownHostException{
		System.out.println("삭제 코멘트번호//"+commentNo);
		if (commentService.deleteComment(commentNo)==1) {
			return "Y";
		}else{
			return "N";
		}
	}
	
	
	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content) throws UnknownHostException{
		System.out.println("코멘트 번호//"+ commentNo+" ----- 코멘트 수정 내용 //"+ content);
		if(commentService.updateComment(content, commentNo)==1){
			return "Y";
		}else{
			return "N";
		}
		
		
	}
	

}
