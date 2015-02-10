package kr.co.kware.common.util.pages;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import kr.co.kware.board.article.vo.Article;

public class Paginator extends SimpleTagSupport {
	
	private Logger logger = Logger.getLogger(Paginator.class);
	private String uri;
	private int currentPage; 
	private int totalPages; //총게시글 수 
	private int maxLinks = 10; 
	private Page<Article> page;
	
	private Writer getWriter(){
		JspWriter out = getJspContext().getOut();
		return out;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		Writer out = getWriter();
		
		boolean lastPage = currentPage == totalPages;
//		int startPage = page.getStartPage();
		int startPage = Math.max(currentPage - maxLinks / 2, 1);
//		int endPage = page.getEndPage();
        int endPage = startPage + maxLinks;
      
        if (endPage > totalPages + 1) {
            int difference = endPage - totalPages;
            startPage -= difference - 1;
        
            if (startPage < 1)
                startPage = 1;
            endPage = totalPages + 1;
        }
 
        try {
        	out.write("<div class=\"paginatorList\">");
//            out.write("<ul class=\"paginatorList\">");
 
            if (currentPage > 1)
                out.write(constructLink(currentPage - 1, "Previous", "paginatorPrev"));
 
            for (int i = startPage; i < endPage; i++) {
                if (i == currentPage)
                    out.write("<span class=\"paginatorCurr"+ (lastPage && i == totalPages ? " paginatorLast" : "")  +"\">"+ "["+currentPage +"]"+ "</span>");
                else
                    out.write(constructLink(i));
            }
 
            if (!lastPage)
                out.write(constructLink(currentPage + 1, "Next", "paginatorNext paginatorLast"));
             
              out.write("</div>");
//            out.write("</ul>");
 
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
		
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
 
    public void setCurrPage(int currPage) {
        this.currentPage = currPage;
        logger.debug("currPage : "+currPage);
    }
 
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        logger.debug("totalPages : "+totalPages);
    }
 
    public void setMaxLinks(int maxLinks) {
    	this.maxLinks = maxLinks;
    	logger.debug("maxLinks : "+maxLinks);
    }
    
    
	
	
	
}
