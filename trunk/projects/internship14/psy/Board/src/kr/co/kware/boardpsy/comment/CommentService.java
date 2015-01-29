package kr.co.kware.boardpsy.comment;

import java.util.List;


public interface CommentService {
	
	public int writeComment(Comment comment, int boardNo, String email);
	public int modifyComment(String content, int commentNo);
	public List<Comment> readCommentListByBoardNo(int boardNo);
	public int deleteComment(int commentNo);
//	public int selectCommentCountByBoardNo(int boardNo);
	public int getLastCommnetNo();
	
}
