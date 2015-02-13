package kr.co.kware.board.article.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import kr.co.kware.board.article.mapper.ArticleMapper;
import kr.co.kware.board.article.vo.Article;
import kr.co.kware.common.dbcommon.DeletionStatus;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	private Logger logger = Logger.getLogger(ArticleServiceImpl.class);

	@Override
	public int writeArticle(Article article) {
		try {
			int lastNo = articleMapper.selectLastNo();
			article.setFamily(lastNo);
//			article.setFlag(1);
			article.setDeletionStatus(DeletionStatus.PRESENT);
			logger.debug("DeletionStatus.PRESENT: "+DeletionStatus.PRESENT);
			article.setParent(0);
			article.setDepth(0);
			article.setIndent(0);
			article.setHitCount(0);
			article.setWritingDate(new Date());
			article.setModifyDate(null);
			article.setWritingIP(InetAddress.getLocalHost().toString());
			article.setModifyIP(null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return articleMapper.insertArticle(article);
	}

	@Override
	public int writeArticleReply(Article article, int articleNo) {
		Article originArticle = articleMapper.selectArticleByArticleNo(articleNo);
		try {
			logger.debug("���� ��������/" + originArticle);
			article.setFamily(originArticle.getFamily());
			article.setParent(originArticle.getArticleNo());
			article.setDepth(originArticle.getDepth() + 1);
			articleMapper.updateArticleDepth(article);
			logger.debug("���� step//" + originArticle.getDepth());
			logger.debug("���� step//" + article.getDepth());
			article.setIndent(originArticle.getIndent() + 1);
			article.setWritingDate(new Date());
			article.setModifyDate(null);
			article.setWritingIP(InetAddress.getLocalHost().toString());
			article.setModifyIP(null);
			article.setHitCount(0);
//			article.setFlag(1);
			article.setDeletionStatus(DeletionStatus.valueOf(1));
			logger.debug("���/" + article);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return articleMapper.insertArticle(article);
	}

	@Override
	public Article readArticlebyArticleNo(int articleNo, boolean isHitCount) {
		Article article = articleMapper.selectArticleByArticleNo(articleNo);
		if (isHitCount) {
			article.setHitCount(article.getHitCount() + 1);
			articleMapper.updateArticle(article);
		}

		return articleMapper.selectArticleByArticleNo(articleNo);
	}

	@Override
	public int getLastArticleNo() {
		logger.debug(articleMapper.selectLastNo());
		return articleMapper.selectLastNo();
	}

	@Override
	public int modifyArticle(Article updatedArticle, int articleNo) {
		Article article = articleMapper.selectArticleByArticleNo(articleNo);

		// �� ����
		try {
			article.setTitle(updatedArticle.getTitle());
			article.setContent(updatedArticle.getContent());
			article.setModifyDate(new Date());
			article.setModifyIP(InetAddress.getLocalHost().toString());
			boolean isHitCount = false;
			if (!isHitCount) {
				article.setHitCount(article.getHitCount());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return articleMapper.updateArticle(article);
	}

	@Override
	public int deleteArticle(Article updatedArticle, int articleNo) {
		Article article = articleMapper.selectArticleByArticleNo(articleNo);
		try {
			article.setModifyDate(new Date());
			article.setModifyIP(InetAddress.getLocalHost().toString());
			boolean isHitCount = false;
			if (!isHitCount) {
				article.setHitCount(article.getHitCount());
			}
			
			//article.setFlag(0); //flag�� 0�̸� �� ���� �Ұ�
			article.setDeletionStatus(DeletionStatus.DELETED);
			logger.debug("������ �Խñ�//" + article);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return articleMapper.deleteArticle(article);
	}

	@Override
	public List<Article> showArticleList(Map<Object, Object> map) {
		return articleMapper.selectArticleList(map);
	}

	@Override
	public int countTotalArticleNumber() {
		return articleMapper.selectArticleCount();
	}

	@Override
	public int countTotalSearchResult(String searchKey, String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		return articleMapper.searchArticleCount(map);
	
	}

}
