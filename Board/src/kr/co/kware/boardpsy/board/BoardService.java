package kr.co.kware.boardpsy.board;

import java.util.List;

import org.springframework.stereotype.Component;

public interface BoardService {
	
	public int writeBoard(Board board);
	public int writeBoardReply(Board board, int boardNo);
	public Board readBoardbyBoardNo(int boardNo, boolean isHitCount);
	public int getLastBoardNo();
	public int modifyBoard(Board updatedBoard, int boardNo, String[] deletedFileList);
	public int deleteBoard(Board board, int boardNo);
	public List<Board> showBoardList(int startRow, int endRow, String searchKey, String searchValue);
	public int countTotalBoardNumber();
	public int countTotalSearchResult(String searchKey, String searchValue);
	
	
}
