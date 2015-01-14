package service;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import vo.Board;

public class TestBoardService {
	public static void main(String[] args) {

		// private static final Logger LOG = Logger.getLogger("");

		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
		BoardService service = (BoardService) ctx.getBean(BoardService.class);

		try {
			Board board = new Board();
			board.setEmail("test@test4");
			board.setTitle("테스트4");
			board.setContent("test4");
			// board.setFamily(3);
			service.writeBoard(board);
			System.out.println(board);
		} catch (UnknownHostException e) {
			// LOG.log(Level.ALL, e.getMessage());
		}
		System.out.println(service.selectBoardCount());

	}
}
