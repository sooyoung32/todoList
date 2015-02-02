package kr.co.kware.board.comment.service;

import java.util.List;

import kr.co.kware.board.comment.vo.Comment;


public interface CommentService {
	
	public int writeComment(Comment comment, int boardNo, String email);
	public int modifyComment(String content, int commentNo);
	public List<Comment> readCommentListByBoardNo(int boardNo);
	public int deleteComment(int commentNo);
//	public int selectCommentCountByBoardNo(int boardNo);
	public int getLastCommnetNo();
	
}
