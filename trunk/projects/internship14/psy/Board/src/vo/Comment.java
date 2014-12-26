package vo;

public class Comment extends DBCommon {
	
	private int commentNo; 
	private int boardNo; 
	private String email; 
	private String content;
	private Member writer;
	private int flag; 
	
	
	
	//getter&setter
	
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getFlag() {
		return flag;
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
		return "Comment [commentNo=" + commentNo + ", boardNo=" + boardNo + ", email=" + email + ", content=" + content
				+ ", writer=" + writer + ", flag=" + flag + "]";
	}
	
	
	
	
	
	
}
