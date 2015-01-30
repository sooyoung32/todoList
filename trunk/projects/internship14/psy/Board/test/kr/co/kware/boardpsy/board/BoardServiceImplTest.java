package kr.co.kware.boardpsy.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BoardServiceImplTest {
	
	ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
	
	BoardService service = (BoardService) ctx.getBean("boardServiceImpl");
	
	@Test
	public void testCountTotalBoardNumber(){
		int result = service.countTotalBoardNumber();
		 assertEquals(236, result, 0);  
		 
	}
	
	@Test
	public void testGetBoardbyBoardNo(){
		Board board = service.readBoardbyBoardNo(13, false);
		Board board13 = service.readBoardbyBoardNo(13, false);
		assertEquals(board13, board);
		
		
	}
	
	
}
