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
		
//		System.out.println(mapper.selectBoardCount());
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<Board> list = mapper.selectBoardList(map);
//		for(Board b : list){
//			System.out.println(b);
//		}
		
		Board b = new Board();
		b.setEmail("soo");
		b.setContent("sss");
		b.setTitle("sss");
		mapper.insertBoard(b);
		System.out.println(b);
		
//		Member m = new Member();
//		m.setEmail("����");
//		m.setName("sooyoung");
//		m.setPassword("0000");
//		memberMapper.insertMember(m);
//		System.out.println(m);
		
	}
}
