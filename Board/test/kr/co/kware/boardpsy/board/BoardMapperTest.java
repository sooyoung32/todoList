package kr.co.kware.boardpsy.board;

import static org.junit.Assert.assertEquals;
import kr.co.kware.boardpsy.comment.CommentMapper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BoardMapperTest {
	ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
	BoardMapper mapper = (BoardMapper) ctx.getBean(BoardMapper.class);
	
	@Test
	public void testCountTotalBoardNumber(){

		int result = mapper.selectBoardCount();
		 assertEquals(236, result, 0);      
	}
	
	@Test
	public void testSelectBoardByBoardNo(){
		Board board = mapper.selectBoardByBoardNo(100);
		assertEquals(board, board);
		
	}
	
	
}
