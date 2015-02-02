package kr.co.kware.board.comment.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;





import kr.co.kware.board.comment.mapper.CommentMapper;
import kr.co.kware.board.comment.vo.Comment;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	private Logger logger = Logger.getLogger(CommentServiceImpl.class); 
	
	
	@Override
	public int writeComment(Comment comment, int boardNo, String email) {
		String content = comment.getContent(); 
		content = content.replaceAll("\r\n", "  ");
		comment.setContent(content);
		logger.debug("코멘트서비스  : "+comment.getContent() );
		comment.setEmail(email);
		comment.setBoardNo(boardNo);
		comment.setFlag(1);
		comment.setWritingDate(new Date());
		try {
			comment.setWritingIP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return commentMapper.insertComment(comment);
	}

	@Override
	public int modifyComment(String content, int commentNo) {
		Comment comment = commentMapper.selectCommentByCommentNo(commentNo);
		comment.setContent(content);
		comment.setModifyDate(new Date());
		try {
			comment.setModifyIP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return commentMapper.updateComment(comment);
	}
	
	
	@Override
	public int deleteComment(int commentNo) {
		Comment comment = commentMapper.selectCommentByCommentNo(commentNo);
		comment.setFlag(0);
		comment.setModifyDate(new Date());
		try {
			comment.setModifyIP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return commentMapper.deleteComment(comment);
	}

	@Override
	public int getLastCommnetNo() {
		return commentMapper.selectCommentLastNo();
	}

	@Override
	public List<Comment> readCommentListByBoardNo(int boardNo) {
		return commentMapper.selectCommentListByBoardNo(boardNo);
	}

}
