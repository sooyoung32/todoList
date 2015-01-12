package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapper.BoardMapper;
import mapper.FileMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Board;
import vo.BoardPage;
import vo.File;

@Component
public class BoardService {

	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private FileMapper fileMapper;

	public int selectLastNo() {
		System.out.println(boardMapper.selectLastNo());
		return boardMapper.selectLastNo();
	}

	public int insertBoard(Board board) throws UnknownHostException {
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
		return boardMapper.insertBoard(board);
	}

	public int insertReply(Board board, int boardNo) throws UnknownHostException {
		Board originBoard = boardMapper.selectBoardByBoardNo(boardNo);
		System.out.println("원글 가져오기/" + originBoard);
		board.setFamily(originBoard.getFamily());
		board.setParent(originBoard.getBoardNo());
		board.setDepth(originBoard.getDepth() + 1);
		System.out.println(boardMapper.updateBoardDepth(board));
		System.out.println("원글 step//" + originBoard.getDepth());
		System.out.println("새글 step//" + board.getDepth());
		board.setIndent(originBoard.getIndent() + 1);
		board.setWritingDate(new Date());
		board.setModifyDate(null);
		board.setWritingIP(InetAddress.getLocalHost().toString());
		board.setModifyIP(null);
		board.setHitCount(0);
		board.setFlag(1);
		System.out.println("답글/" + board);
		return boardMapper.insertBoard(board);
	}

	public Board selectBoardByBoardNo(int boardNo, boolean isHitCount) {

		Board board = boardMapper.selectBoardByBoardNo(boardNo);
		if (isHitCount) {
			board.setHitCount(board.getHitCount() + 1);
			boardMapper.updateBoard(board);
		}

		return boardMapper.selectBoardByBoardNo(boardNo);
	}

	public Integer selectBoardCount() {
		return boardMapper.selectBoardCount();
	}

	public int updateBoard(Board updatedBoard, int boardNo, String[] deletedFileList) throws UnknownHostException {

		// 글 수정
		Board board = boardMapper.selectBoardByBoardNo(boardNo);
		board.setTitle(updatedBoard.getTitle());
		board.setContent(updatedBoard.getContent());
		board.setModifyDate(new Date());
		board.setModifyIP(InetAddress.getLocalHost().toString());
		boolean isHitCount = false;
		if (!isHitCount) {
			board.setHitCount(board.getHitCount());
		}

		return boardMapper.updateBoard(board);
	}

	public int deleteBoard(Board updatedBoard, int boardNo) throws UnknownHostException {

		Board board = boardMapper.selectBoardByBoardNo(boardNo);
		board.setModifyDate(new Date());
		board.setModifyIP(InetAddress.getLocalHost().toString());
		boolean isHitCount = false;
		if (!isHitCount) {
			board.setHitCount(board.getHitCount());
		}

		board.setFlag(0);

		System.out.println("업데이트된 게시글//" + board);
		return boardMapper.deleteBoard(board);
	}

	public List<Board> selectBoardList(int startRow, int endRow, String searchKey, String searchValue) {

		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow - startRow + 1);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		// System.out.println("map//"+map);
		return boardMapper.selectBoardList(map);

	}

	public int searchBoardCount(String searchKey, String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println("searchBoardCount!!" + searchKey + "//" + searchValue);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		return boardMapper.searchBoardCount(map);
	}

}
