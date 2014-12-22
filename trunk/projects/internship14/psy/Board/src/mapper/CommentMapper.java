package mapper;

import java.util.List;

import vo.Comment;

public interface CommentMapper {
	
	public int insertComment(Comment comment); 
	public int updateComment(Comment comment);
	public int deleteComment(int commentNo);
	public List<Comment> selectCommentListByBoardNo(int boardNo);
	public int selectCommentCountByBoardNo();
	public Comment selectCommentByCommentNo(int commentNo);
	
	
}
