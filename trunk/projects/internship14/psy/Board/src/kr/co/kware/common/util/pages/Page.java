package kr.co.kware.common.util.pages;

import java.util.List;

import org.apache.ibatis.type.Alias;

public class Page<T> {
	
	private int totalRecodeCount;   //�� ���ڵ� �� 
	private int startPage;			//���� ������ 
	private int endPage;			//�� ������
 	private int startRow; 			//�� �������� ������ ���� �� ��ȣ 
	private int endRow;				//                   ������ �� ��ȣ
	private int finalPage;			//������ ������ 
	private int prePage;			//�� ������
	private int nextPage;			//���� ������ 
	private int currentPageNo;      //������ ��ȣ
	private List<T> dataList;	    //������ ����Ʈ 
	private int pageSize; 			//���������� ���̴� ��ƼŬ ����
	

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
		
		// ���� ������
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
		
		boolean isNowFirst = currentPageNo == 1 ? true : false; // ���� ������ (��ü)
		boolean isNowFinal = currentPageNo == finalPage ? true : false; // ������ ������ (��ü)
		
		if (isNowFirst) {
			prePage = 1; // ���� ������ ��ȣ
		} else {
			prePage = (((currentPageNo - 1) < 1 ? 1 : (currentPageNo - 1))); // ���� ������ ��ȣ
		}
		
		if (isNowFinal) {
			nextPage = (finalPage); // ���� ������ ��ȣ
		} else {
			nextPage = (((currentPageNo + 1) > finalPage ? finalPage : (currentPageNo + 1))); // ���� ������ ��ȣ
		}
		
		
	}
	
}
