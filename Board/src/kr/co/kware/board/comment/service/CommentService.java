package kr.co.kware.board.comment.service;

import java.util.List;

import kr.co.kware.board.comment.vo.Comment;


public interface CommentService {
	
	public int writeComment(Comment comment, int articleNo, String email);
	public int modifyComment(String content, int commentNo);
	public List<Comment> readCommentListByArticleNo(int articleNo);
	public int deleteComment(int commentNo);
//	public int selectCommentCountByArticleNo(int articleNo);
	public int getLastCommnetNo();
	
}
