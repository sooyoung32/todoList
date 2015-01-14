package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import mapper.CommentMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Comment;

@Component
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	private Logger logger = Logger.getLogger(CommentService.class); 
	
	
	public int writeComment(Comment comment, int boardNo, String email) throws UnknownHostException {
		String content = comment.getContent(); 
		content = content.replaceAll("\r\n", "  ");
		comment.setContent(content);
		logger.debug("코멘트서비스  : "+comment.getContent() );
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

	public List<Comment> readCommentListByBoardNo(int boardNo) {
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
