package kr.co.kware.boardpsy.board;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	private Logger logger = Logger.getLogger(BoardServiceImpl.class);

	@Override
	public int writeBoard(Board board) {
		try {
			int lastNo = boardMapper.selectLastNo();
			board.setFamily(lastNo);
			board.setFlag(1);
			board.setParent(0);
			board.setDepth(0);
			board.setIndent(0);
			board.setHitCount(0);
			board.setWritingDate(new Date());
			board.setModifyDate(null);
			board.setWritingIP(InetAddress.getLocalHost().toString());
			board.setModifyIP(null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return boardMapper.insertBoard(board);
	}

	@Override
	public int writeBoardReply(Board board, int boardNo) {
		Board originBoard = boardMapper.selectBoardByBoardNo(boardNo);
		try {
			logger.debug("원글 가져오기/" + originBoard);
			board.setFamily(originBoard.getFamily());
			board.setParent(originBoard.getBoardNo());
			board.setDepth(originBoard.getDepth() + 1);
			boardMapper.updateBoardDepth(board);
			logger.debug("원글 step//" + originBoard.getDepth());
			logger.debug("새글 step//" + board.getDepth());
			board.setIndent(originBoard.getIndent() + 1);
			board.setWritingDate(new Date());
			board.setModifyDate(null);
			board.setWritingIP(InetAddress.getLocalHost().toString());
			board.setModifyIP(null);
			board.setHitCount(0);
			board.setFlag(1);
			logger.debug("답글/" + board);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return boardMapper.insertBoard(board);
	}

	@Override
	public Board readBoardbyBoardNo(int boardNo, boolean isHitCount) {
		Board board = boardMapper.selectBoardByBoardNo(boardNo);
		if (isHitCount) {
			board.setHitCount(board.getHitCount() + 1);
			boardMapper.updateBoard(board);
		}

		return boardMapper.selectBoardByBoardNo(boardNo);
	}

	@Override
	public int getLastBoardNo() {
		logger.debug(boardMapper.selectLastNo());
		return boardMapper.selectLastNo();
	}

	@Override
	public int modifyBoard(Board updatedBoard, int boardNo, String[] deletedFileList) {
		Board board = boardMapper.selectBoardByBoardNo(boardNo);

		// 글 수정
		try {
			board.setTitle(updatedBoard.getTitle());
			board.setContent(updatedBoard.getContent());
			board.setModifyDate(new Date());
			board.setModifyIP(InetAddress.getLocalHost().toString());
			boolean isHitCount = false;
			if (!isHitCount) {
				board.setHitCount(board.getHitCount());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return boardMapper.updateBoard(board);
	}

	@Override
	public int deleteBoard(Board updatedBoard, int boardNo) {
		Board board = boardMapper.selectBoardByBoardNo(boardNo);
		try {
			board.setModifyDate(new Date());
			board.setModifyIP(InetAddress.getLocalHost().toString());
			boolean isHitCount = false;
			if (!isHitCount) {
				board.setHitCount(board.getHitCount());
			}
			
			board.setFlag(0); //flag가 0이면 글 열람 불가
			
			System.out.println("업데이트된 게시글//" + board);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return boardMapper.deleteBoard(board);
	}

	@Override
	public List<Board> showBoardList(int startRow, int endRow, String searchKey, String searchValue) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow - startRow + 1);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		return boardMapper.selectBoardList(map);
	}

	@Override
	public int countTotalBoardNumber() {
		return boardMapper.selectBoardCount();
	}

	@Override
	public int countTotalSearchResult(String searchKey, String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		return boardMapper.searchBoardCount(map);
	
	}

}
