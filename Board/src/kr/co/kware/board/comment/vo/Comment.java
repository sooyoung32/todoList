package kr.co.kware.board.comment.vo;

import kr.co.kware.common.dbcommon.DBCommon;
import kr.co.kware.common.dbcommon.DeletionStatus;
import kr.co.kware.member.vo.Member;

public class Comment extends DBCommon {
	
	private int commentNo; 
	private int articleNo; 
	private String email; 
	private String content;
	private kr.co.kware.member.vo.Member writer;
//	private int flag; 
	DeletionStatus deletionStatus;
	
	
	
	//getter&setter
	
	
//	public void setFlag(int flag) {
//		this.flag = flag;
//	}
//	
//	public int getFlag() {
//		return flag;
//	}
	
	public void setDeletionStatus(DeletionStatus deletionStatus) {
		this.deletionStatus = deletionStatus;
	}
	
	public DeletionStatus getDeletionStatus() {
		return deletionStatus;
	}
	
	public Member getWriter() {
		return writer;
	}
	
	public void setWriter(Member writer) {
		this.writer = writer;
	}
	
	
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", articleNo=" + articleNo + ", email=" + email + ", content=" + content
				+ ", writer=" + writer + ", deletionStatus=" + deletionStatus + "]";
	}

//	@Override
//	public String toString() {
//		return "Comment [commentNo=" + commentNo + ", articleNo=" + articleNo + ", email=" + email + ", content=" + content
//				+ ", writer=" + writer + ", flag=" + flag + "]";
//	}
	
	
	
	
	
	
}
