package kr.co.kware.board.article.vo;

import java.util.List;

import kr.co.kware.board.comment.vo.Comment;
import kr.co.kware.board.file.vo.File;
import kr.co.kware.common.dbcommon.DBCommon;
import kr.co.kware.common.dbcommon.DeletionStatus;
import kr.co.kware.member.vo.Member;

import org.apache.ibatis.type.Alias;

public class Article extends DBCommon{
	
	private int articleNo; 
	private String email; 
	private String title; 
	private String content;
	private int hitCount; 
	private Member writer; 
	private List<File> files; 
	private List<Comment> comments; 
	private DeletionStatus deletionStatus;  
//	private int flag; //TODO 이름 바꾸기 Enum type
	
	//답글을위한 부가정보.. 
	private int family; 
	private int parent; 
	private int depth; 
	private int indent; 
	
	
	//getter&setter
	
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	
	public int getHitCount() {
		return hitCount;
	}
	
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	
	public int getArticleNo() {
		return articleNo;
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

//	public int getFlag() {
//		return flag;
//	}
//	
//	public void setFlag(int flag) {
//		this.flag = flag;
//	}
	
	public DeletionStatus getDeletionStatus() {
		return deletionStatus;
	}
	
	public void setDeletionStatus(DeletionStatus deletionStatus) {
		
		this.deletionStatus = deletionStatus;
	}

	@Override
	public String toString() {
		return "Article [articleNo=" + articleNo + ", email=" + email + ", title=" + title + ", content=" + content
				+ ", hitCount=" + hitCount + ", writer=" + writer + ", files=" + files + ", comments=" + comments
				+ ", deletionStatus=" + deletionStatus + ", family=" + family + ", parent=" + parent + ", depth="
				+ depth + ", indent=" + indent + "]";
	}

//	@Override
//	public String toString() {
//		return "Board [boardNo=" + articleNo + ", email=" + email + ", title=" + title + ", content=" + content
//				+ ", family=" + family + ", parent=" + parent + ", depth=" + depth + ", indent=" + indent
//				+ ", hitCount=" + hitCount + ", writer=" + writer + ", files=" + files + ", comments=" + comments
//				+ ", flag=" + flag + "]";
//	}

	
	
}
