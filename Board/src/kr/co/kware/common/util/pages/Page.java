package kr.co.kware.common.util.pages;

import java.util.List;

import org.apache.ibatis.type.Alias;

public class Page<T> {
	
	private int totalRecodeCount;   //총 레코드 수 
	private int startPage;			//시작 페이지 
	private int endPage;			//끝 페이지
 	private int startRow; 			//한 페이지에 보여줄 시작 글 번호 
	private int endRow;				//                   마지막 글 번호
	private int finalPage;			//마지막 페이지 
	private int prePage;			//전 페이지
	private int nextPage;			//다음 페이지 
	private int currentPageNo;      //페이지 번호
	private List<T> dataList;	    //데이터 리스트 
	private int pageSize; 			//한페이지당 보이는 아티클 개수
	

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public int getTotalRecodeCount() {
		return totalRecodeCount;
	}
	public void setTotalRecodeCount(int totalRecodeCount) {
		this.totalRecodeCount = totalRecodeCount;
		this.makePage();
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
	public int getFinalPage() {
		return finalPage;
	}
	public void setFinalPage(int finalPage) {
		this.finalPage = finalPage;
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
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}


	@Override
	public String toString() {
		return "Page [totalRecodeCount=" + totalRecodeCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", startRow=" + startRow + ", endRow=" + endRow + ", finalPage=" + finalPage + ", prePage=" + prePage
				+ ", nextPage=" + nextPage + ", currentPageNo=" + currentPageNo + ", dataList=" + dataList
				+ ", pageSize=" + pageSize + "]";
	}

	public void makePage(){
		
		if(this.totalRecodeCount == 0){
			return; 
		}
		
		if(this.pageSize == 0){
			this.setPageSize(15);
		}
		
		if(this.currentPageNo == 0) {
			this.setCurrentPageNo(1);
		}
		
		finalPage = totalRecodeCount / pageSize;

		if (totalRecodeCount % pageSize != 0) {
			finalPage++;
		}
		
		// 시작 페이지
		startPage = 1;
		if (startPage <= 0) {
			startPage = 1;
		}
		endPage = startPage + 9;
		
		if (endPage > finalPage) {
			endPage = finalPage;
		}
		
		while (currentPageNo > endPage) {
			startPage = startPage + 10;
			endPage = startPage + 9;
			
			if (endPage > finalPage) {
				endPage = finalPage;
			}
			
		}
		
		boolean isNowFirst = currentPageNo == 1 ? true : false; // 시작 페이지 (전체)
		boolean isNowFinal = currentPageNo == finalPage ? true : false; // 마지막 페이지 (전체)
		
		if (isNowFirst) {
			prePage = 1; // 이전 페이지 번호
		} else {
			prePage = (((currentPageNo - 1) < 1 ? 1 : (currentPageNo - 1))); // 이전 페이지 번호
		}
		
		if (isNowFinal) {
			nextPage = (finalPage); // 다음 페이지 번호
		} else {
			nextPage = (((currentPageNo + 1) > finalPage ? finalPage : (currentPageNo + 1))); // 다음 페이지 번호
		}
		
		
	}
	
}
