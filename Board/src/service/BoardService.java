package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapper.BoardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Board;
import vo.BoardPage;

@Component
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	public int selectLastNo(){
		System.out.println(mapper.selectLastNo());
		return mapper.selectLastNo();
	}
	
	public int insertBoard(Board board) throws UnknownHostException{
		int lastNo = mapper.selectLastNo();
		board.setFamily(lastNo);
		
		board.setParent(0);
		board.setDepth(0);
		board.setIndent(0);
		board.setHitCount(0);
		board.setWritingDate(new Date());
		board.setModifyDate(null);
		board.setWritingIP(InetAddress.getLocalHost().toString());
		board.setModifyIP(null);
		return mapper.insertBoard(board);
	}
	
	
	public int insertReply(Board board, int boardNo) throws UnknownHostException{
		Board originBoard = mapper.selectBoardByBoardNo(boardNo); 
		System.out.println("원글 가져오기/"+originBoard);
		board.setFamily(originBoard.getFamily());
		board.setParent(originBoard.getBoardNo());
		board.setDepth(originBoard.getDepth()+1);
		System.out.println(mapper.updateBoardDepth(board));
		System.out.println("원글 step//"+originBoard.getDepth());
		System.out.println("새글 step//"+board.getDepth());
		board.setIndent(originBoard.getIndent()+1);
		board.setWritingDate(new Date());
		board.setModifyDate(null);
		board.setWritingIP(InetAddress.getLocalHost().toString());
		board.setModifyIP(null);
		board.setHitCount(0);
		System.out.println("답글/"+board);
		return mapper.insertBoard(board);
	}
	
	public Board selectBoardByBoardNo(int boardNo, boolean isHitCount){
		
		Board board = mapper.selectBoardByBoardNo(boardNo);
		if(isHitCount){
			board.setHitCount(board.getHitCount()+1);
			mapper.updateBoard(board);
		}
		
		return mapper.selectBoardByBoardNo(boardNo);
	}
	
	public Integer selectBoardCount(){
		return mapper.selectBoardCount();
	}
	
	public int updateBoard(Board board, int boardNo) throws UnknownHostException{
		board = mapper.selectBoardByBoardNo(boardNo);
		board.setModifyDate(new Date());
		board.setModifyIP(InetAddress.getLocalHost().toString());
		boolean isHitCount = false; 
		if(!isHitCount){
			board.setHitCount(board.getHitCount());
		}
		return mapper.updateBoard(board);
	}
	
	
	public int deleteBoard(int boardNo){
		return mapper.deleteBoard(boardNo);
	}
	
	public List<Board> selectBoardList(int startRow, int endRow){
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow-startRow+1);
		return mapper.selectBoardList(map);
	}
	
	
}
