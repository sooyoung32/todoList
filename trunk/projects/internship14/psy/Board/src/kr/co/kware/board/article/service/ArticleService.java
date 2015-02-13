package kr.co.kware.board.article.service;

import java.util.List;
import java.util.Map;

import kr.co.kware.board.article.vo.Article;

public interface ArticleService {
	
	public int writeArticle(Article article);
	public int writeArticleReply(Article article, int articleNo);
	public Article readArticlebyArticleNo(int articleNo, boolean isHitCount);
	public int getLastArticleNo();
	public int modifyArticle(Article updatedArticle, int articleNo);
	public int deleteArticle(Article article, int articleNo);
//	public List<Article> showArticleList(int startRow, int endRow, String searchKey, String searchValue);
	public List<Article> showArticleList(Map<Object, Object> map);
	public int countTotalArticleNumber();
	public int countTotalSearchResult(String searchKey, String searchValue);
	
	
}
