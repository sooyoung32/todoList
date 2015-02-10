package kr.co.kware.common.util.pages;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class MyPaging<T> extends SimpleTagSupport {

	private Logger logger = Logger.getLogger(MyPaging.class);
	private int totalRecordCount;
	private int numOfRecordPerPage;
	private int currentPage;
	private int startPage;
	private int endPage;
	private int prePage;
	private int nextPage;
	private int finalPage;
	private int firstPage;
	
	private Page<T> page;
	private String uri;
	
	private Writer getWriter() {
		JspWriter out = getJspContext().getOut();
		return out;
	}

	@Override
	public void doTag() throws JspException, IOException {
		Writer out = getWriter();

		startPage = page.getStartPage();
		logger.debug("startPage = " + startPage);

		endPage = page.getEndPage();
		logger.debug("endPage = " + endPage);
		
		finalPage = page.getFinalPage();
		logger.debug("finalPage = " + finalPage);
		
		totalRecordCount = page.getTotalRecodeCount();
		logger.debug("totalRecordCount = "+totalRecordCount);
		
		boolean isNowFirst = currentPage == 1 ? true : false; // ���� ������ (��ü)
		boolean isNowFinal = currentPage == finalPage ? true : false; // ������ ������ (��ü)
		
		for (int i = startPage; i < endPage; i++) {

			out.write("<div class=\"paginatorList\">");
			// out.write("<ul class=\"paginatorList\">");
			
			if(isNowFirst){
				constructLink(1);
			}else{
				out.write(constructLink(currentPage - 1, "Previous", "paginatorPrev"));
			}
			
			//if (currentPage > 1)
//				out.write(constructLink(firstPage, "First", "paginatorFirst"));
				
//			for (int i = startPage; i < endPage; i++) {
			if (i == currentPage){  //i�� ���� ��������� a ��ũǥ�� ���Ѵ�. 
				out.write("<span class=\"paginatorCurr" + (isNowFinal && i == endPage ? " paginatorLast" : " ")
						+ "\">" + "[" + currentPage + "]" + "</span>");
			}else{
				out.write(constructLink(i));
			}
//			}

			//if (!isNowFinal){
				out.write(constructLink(currentPage + 1, "Next", "paginatorNext"));
				out.write(constructLink(finalPage, "Last", "paginatorLast"));
			//}
			
			out.write("</div>");
		}
	}
	private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }
 
    private String constructLink(int page, String text, String className) {
        StringBuilder link = new StringBuilder("<span");  //li�� span���� ����
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
}
