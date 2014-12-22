package vo;

public class Comment extends DBCommon {
	
	private int commentNo; 
	private int boardNo; 
	private String email; 
	private String content;
	private Member writer;
	
	//getter&setter
	
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
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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
		return "Comment [commentNo=" + commentNo + ", boardNo=" + boardNo
				+ ", email=" + email + ", content=" + content + "]";
	}

	
	
	
	
}
