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

}
