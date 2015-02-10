package kr.co.kware.common.util.pages;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class MyPaging<T> extends SimpleTagSupport {

	private Logger logger = Logger.getLogger(MyPaging.class);
	private int totalPages;
	private int numOfRecordPerPage;
	private int currPage;
	private int startPage;
	private int endPage;
	private int prePage;
	private int nextPage;
	private int finalPage;
	private int firstPage;
	private int maxLinks = 10;
	
	private Page<T> page = new Page<T>();
	
	private String uri;
	
	
	private Writer getWriter() {
		JspWriter out = getJspContext().getOut();
		return out;
	}

	@Override
	public void doTag() throws JspException, IOException {
		Writer out = getWriter();
		
		logger.debug("page = "+page);
		
		startPage = page.getStartPage();
		logger.debug("startPage = " + startPage);

		endPage = page.getEndPage();
		logger.debug("endPage = " + endPage);
		
		finalPage = page.getFinalPage();
		logger.debug("finalPage = " + finalPage);
		
		totalPages = page.getTotalRecodeCount();
		logger.debug("totalRecordCount = "+totalPages);
		
		boolean isNowFirst = currPage == 1 ? true : false; // 시작 페이지 (전체)
		boolean isNowFinal = currPage == finalPage ? true : false; // 마지막 페이지 (전체)
		
		out.write("<div class=\"paginatorList\">");
	
		out.write(constructLink(firstPage, "First", "paginatorFirst"));
		out.write(constructLink(currPage - 1, "Previous", "paginatorPrev"));

		for (int i = startPage; i <= endPage; i++) {

			// out.write("<ul class=\"paginatorList\">");
			
			
			//if (currentPage > 1)
				
//			for (int i = startPage; i < endPage; i++) {
			if (i == currPage){  //i가 현재 페이지라면 a 링크표시 안한다. 
				out.write("<span class=\"paginatorCurr" + (isNowFinal && i == endPage ? " paginatorLast" : " ")
						+ "\">" + "[" + currPage + "]" + "</span>");
			}else{
				out.write(constructLink(i));
			}
//			}

			//if (!isNowFinal){
			//}
			
		}
		out.write(constructLink(currPage + 1, "Next", "paginatorNext"));
		out.write(constructLink(finalPage, "Last", "paginatorLast"));
		out.write("</div>");
	}
	private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }
 
    private String constructLink(int page, String text, String className) {
        StringBuilder link = new StringBuilder("<span");  //li를 span으로 변경
        if (className != null) {
            link.append(" class=\"");
            link.append(className);
            link.append("\"");
        }
        link.append(">")
            .append("<a href=\"")
            .append(uri.replace("##", String.valueOf(page)))
            .append("\">")
            .append("["+text+"]")
            .append("</a></span>");
        return link.toString();
    }
 
    
    
    
    public void setUri(String uri) {
        this.uri = uri;
        logger.debug("uri : "+uri);
    }

	public String getUri() {
		return uri;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setTotalPages(int totalRecordCount) {
		this.totalPages = totalRecordCount;
	}

	public void setNumOfRecordPerPage(int numOfRecordPerPage) {
		this.numOfRecordPerPage = numOfRecordPerPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setFinalPage(int finalPage) {
		this.finalPage = finalPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public void setMaxLinks(int maxLinks) {
		this.maxLinks = maxLinks;
	}

	public void setPage(Page page) {
		this.page = page;
	}
    
    
    
    
    
    
}
