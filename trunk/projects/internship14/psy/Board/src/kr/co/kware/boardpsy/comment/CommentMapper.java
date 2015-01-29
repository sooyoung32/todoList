package kr.co.kware.boardpsy.comment;

import java.util.List;


public interface CommentMapper {
	
	public int insertComment(Comment comment); 
	public int updateComment(Comment comment);
	public int deleteComment(Comment comment);
	public List<Comment> selectCommentListByBoardNo(int boardNo);
	public int selectCommentCountByBoardNo();
	public Comment selectCommentByCommentNo(int commentNo);
	public int selectCommentLastNo();
	
	
}
