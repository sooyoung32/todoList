package service;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import vo.Board;

public class TestBoardService {
	public static void main(String[] args) {

//		private static final Logger LOG = Logger.getLogger("");

		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
		BoardService service = (BoardService) ctx.getBean(BoardService.class);

		
		try {
			Board board = new Board();
			board.setEmail("jinsaja@gmail.com");
			board.setTitle("타이틀111111");
			board.setContent("본문내용...1");
//			board.setFamily(3);
			service.insertBoard(board);
			System.out.println(board);
		} catch (UnknownHostException e) {
//			LOG.log(Level.ALL, e.getMessage());
		}
		System.out.println(service.selectBoardCount()
				);
		
	}
}
