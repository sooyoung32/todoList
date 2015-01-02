package vo;

import java.util.List;

public class BoardPage {
	
	private int totalBoardCount;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private int totalPage;
	private int prePage;
	private int nextPage;
	private int finalPage; 
	private List<Board> boardList;
	public int getTotalBoardCount() {
		return totalBoardCount;
	}
	public void setTotalBoardCount(int totalBoardCount) {
		this.totalBoardCount = totalBoardCount;
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
	public int getFinalPage() {
		return finalPage;
	}
	public void setFinalPage(int finalPage) {
		this.finalPage = finalPage;
	}
	public List<Board> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}
	public BoardPage(int totalBoardCount, int startPage, int endPage,
			int startRow, int endRow, int totalPage, int prePage, int nextPage,
			int finalPage, List<Board> boardList) {
		super();
		this.totalBoardCount = totalBoardCount;
		this.startPage = startPage;
		this.endPage = endPage;
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalPage = totalPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.finalPage = finalPage;
		this.boardList = boardList;
	}
	@Override
	public String toString() {
		return "BoardPage [totalBoardCount=" + totalBoardCount + ", startPage="
				+ startPage + ", endPage=" + endPage + ", startRow=" + startRow
				+ ", endRow=" + endRow + ", totalPage=" + totalPage
				+ ", prePage=" + prePage + ", nextPage=" + nextPage
				+ ", finalPage=" + finalPage + ", boardList=" + boardList + "]";
	}
	

	
	
}
