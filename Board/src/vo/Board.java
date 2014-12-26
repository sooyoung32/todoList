package vo;

import java.util.Date;
import java.util.List;


public class Board extends DBCommon{
	
	private int boardNo; 
	private String email; 
	private String title; 
	private String content;
	private int family; 
	private int parent; 
	private int depth; 
	private int indent; 
	private int hitCount; 
	private Member writer; 
	private List<File> files; 
	private List<Comment> comments; 
	private int flag;
	
	
	//getter&setter
	
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	
	public int getHitCount() {
		return hitCount;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFamily() {
		return family;
	}
	public void setFamily(int family) {
		this.family = family;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}

	public Member getWriter() {
		return writer;
	}

	public void setWriter(Member writer) {
		this.writer = writer;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getFlag() {
		return flag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", email=" + email + ", title=" + title + ", content=" + content
				+ ", family=" + family + ", parent=" + parent + ", depth=" + depth + ", indent=" + indent
				+ ", hitCount=" + hitCount + ", writer=" + writer + ", files=" + files + ", comments=" + comments
				+ ", flag=" + flag + "]";
	}
	

	
	
	
	
}
