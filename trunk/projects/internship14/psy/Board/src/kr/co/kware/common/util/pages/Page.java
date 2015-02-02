package kr.co.kware.common.util.pages;

import java.util.List;

import org.apache.ibatis.type.Alias;

public class Page<T> {
	
	private int totalArticleCount;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private int totalPage;
	private int prePage;
	private int nextPage;
	private int pageNo; 
	private List<T> articleList;
	
	//TODO 여기에서 페이지 계산 처리. 

	public Page(int totalArticleCount, int startPage, int endPage, int startRow, int endRow, int totalPage,
			int prePage, int nextPage, int pageNo, List<T> articleList) {
		super();
		this.totalArticleCount = totalArticleCount;
		this.startPage = startPage;
		this.endPage = endPage;
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalPage = totalPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.pageNo = pageNo;
		this.articleList = articleList;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	
	public int getTotalArticleCount() {
		return totalArticleCount;
	}
	public void setTotalArticleCount(int totalArticleCount) {
		this.totalArticleCount = totalArticleCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public List<T> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<T> articleList) {
		this.articleList = articleList;
	}

	@Override
	public String toString() {
		return "Page [totalArticleCount=" + totalArticleCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", startRow=" + startRow + ", endRow=" + endRow + ", totalPage=" + totalPage + ", prePage=" + prePage
				+ ", nextPage=" + nextPage + ", pageNo=" + pageNo + ", articleList=" + articleList + "]";
	}
	
	
}
