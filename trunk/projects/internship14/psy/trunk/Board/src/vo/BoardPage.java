package vo;

import java.util.List;

public class BoardPage {
	
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private int totalPage;
	private List<Board> boardList;	
	
	public BoardPage(List<Board> boardList, int startPage, int endPage,
			int startRow, int endRow, int totalPage) {
		super();
		this.boardList = boardList;
		this.startPage = startPage;
		this.endPage = endPage;
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalPage = totalPage;
	}
	
	
	public List<Board> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
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

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	
	
}
