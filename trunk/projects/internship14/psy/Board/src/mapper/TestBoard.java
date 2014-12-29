package mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import vo.Board;
import vo.Member;

public class TestBoard {
	public static void main(String[] args) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
		BoardMapper mapper = (BoardMapper) ctx.getBean(BoardMapper.class);
		MemberMapper memberMapper = (MemberMapper)ctx.getBean(MemberMapper.class);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", "ALL");
		map.put("searchValue", "qqq");
//		map.put("startRow", 0);
//		map.put("endRow", 10);
		List<Board> list = mapper.searchBoardList(map);
	
		for(Board b : list){
			System.out.println(b);
		}
		
		System.out.println("보드카운트//"+mapper.searchBoardCount(map));

		
	}
}
