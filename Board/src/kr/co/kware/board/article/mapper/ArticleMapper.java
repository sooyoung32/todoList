package kr.co.kware.board.article.mapper;

import java.util.List;
import java.util.Map;

import kr.co.kware.board.article.vo.Article;


public interface ArticleMapper {
	
	public int insertArticle(Article article); 
	public int updateArticle(Article article);
	public int deleteArticle(Article article);
	public Article selectArticleByArticleNo(int articleNo);
	public List<Article> selectArticleList(Map<String, Object> map);
	public int selectArticleCount();
	public int insertArticleReply(Article article); 
	public int updateArticleDepth(Article article);
	public Article selectArticleCountByParent(); 
	public int selectLastNo();
	public int updateFamily();
	public List<Article> searchArticleList(Map<String, Object> map);
	public int searchArticleCount(Map<String, Object> map);
	
	
	
}
