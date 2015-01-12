package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import mapper.CommentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Comment;

@Component
public class CommentService {
	@Autowired
	CommentMapper commentMapper;

	public int insertComment(Comment comment, int boardNo, String email) throws UnknownHostException {
		comment.setEmail(email);
		comment.setBoardNo(boardNo);
		comment.setFlag(1);
		comment.setWritingDate(new Date());
		comment.setWritingIP(InetAddress.getLocalHost().toString());
		return commentMapper.insertComment(comment);
	}

	public int updateComment(String content, int commentNo) throws UnknownHostException {
		Comment comment = commentMapper.selectCommentByCommentNo(commentNo);
		comment.setContent(content);
		comment.setModifyDate(new Date());
		comment.setModifyIP(InetAddress.getLocalHost().toString());
		return commentMapper.updateComment(comment);
	}

	public List<Comment> selectCommentListByBoardNo(int boardNo) {
		return commentMapper.selectCommentListByBoardNo(boardNo);
	}

	public int deleteComment(int commentNo) throws UnknownHostException {
		Comment comment = commentMapper.selectCommentByCommentNo(commentNo);
		comment.setFlag(0);
		comment.setModifyDate(new Date());
		comment.setModifyIP(InetAddress.getLocalHost().toString());
		return commentMapper.deleteComment(comment);
	}

	public int selectCommentCountByBoardNo(int boardNo) {
		return commentMapper.selectCommentCountByBoardNo();
	}

	public int selectCommentLastNo() {
		return commentMapper.selectCommentLastNo();
	}

}
