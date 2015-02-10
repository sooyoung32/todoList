package kr.co.kware.board.comment.mapper;

import java.util.List;

import kr.co.kware.board.comment.vo.Comment;


public interface CommentMapper {
	
	public int insertComment(Comment comment); 
	public int updateComment(Comment comment);
	public int deleteComment(Comment comment);
	public List<Comment> selectCommentListByArticleNo(int articleNo);
	public int selectCommentCountByArticleNo();
	public Comment selectCommentByCommentNo(int commentNo);
	public int selectCommentLastNo();
	
	
}
