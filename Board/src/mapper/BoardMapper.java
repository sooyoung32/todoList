package mapper;

import java.util.List;
import java.util.Map;

import vo.Board;

public interface BoardMapper {
	
	public int insertBoard(Board board); 
	public int updateBoard(Board board);
	public int deleteBoard(Board board);
	public Board selectBoardByBoardNo(int boardNo);
	public List<Board> selectBoardList(Map<String, Object> map);
	public int selectBoardCount();
	public int insertBoardReply(Board board); 
	public int updateBoardDepth(Board board);
	public Board selectBoardCountByParent(); 
	public int selectLastNo();
	public int updateFamily();
	
	
}
