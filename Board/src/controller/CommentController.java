package controller;

import java.net.UnknownHostException;
import java.util.List;

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

	@RequestMapping(value = "insertComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertComment(Comment comment, int boardNo, String email) throws UnknownHostException {

		int commentNo = commentService.selectCommentLastNo();
		comment.setCommentNo(commentNo);
		System.out.println("comment°´Ã¼/" + comment);
		System.out.println("boardNo/" + boardNo);
		System.out.println("ÄÚ¸àÆ® email/ " + email);
		System.out.println("ÄÚ¸àÆ® commentNo/ " + commentNo);

		String result = null;
		if (commentService.insertComment(comment, boardNo, email) == 1) {
			result = "Y";
			System.out.println("result/" + result);
			return result;
		} else {
			result = "N";
			System.out.println("result/" + result);
			return result;
		}

	}
	
	@RequestMapping(value = "deleteComment.do")
	@ResponseBody
	public String deleteComment(int commentNo) throws UnknownHostException{
		System.out.println("»èÁ¦ ÄÚ¸àÆ®¹øÈ£//"+commentNo);
		if (commentService.deleteComment(commentNo)==1) {
			return "Y";
		}else{
			return "N";
		}
	}
	
	
	@RequestMapping(value = "updateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(int commentNo, String content) throws UnknownHostException{
		System.out.println("ÄÚ¸àÆ® ¹øÈ£//"+ commentNo+" ----- ÄÚ¸àÆ® ¼öÁ¤ ³»¿ë //"+ content);
		if(commentService.updateComment(content, commentNo)==1){
			return "Y";
		}else{
			return "N";
		}
		
		
	}
	

}
