package service;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import vo.Comment;
import vo.Member;

public class TestMemberService {
	public static void main(String[] args) throws UnknownHostException {
		
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"D:/workspace/Board_psy/WebContent/WEB-INF/applicationContext.xml");
//		MemberService memberService = (MemberService) ctx.getBean(MemberService.class);
		CommentService commentService = (CommentService)ctx.getBean(CommentService.class);
//		Member m = new Member();
//		m.setEmail("yoona");
//		m.setName("윤아");
//		m.setPassword("0000");
//		memberService.insertMember(m);
//		System.out.println(m);
		
//		m.setPassword("1111");
//		m.setEmail("yoona");
//		System.out.println(memberService.updateMember(m,"0000"));
//		System.out.println(m);
		
//		String login = memberService.loginCheck("yoona", "0000");
//		System.out.println(login);
//		
		Comment c = new Comment(); 
//		c.setEmail("soo");
//		c.setContent("2번글");
//		commentService.insertComment(c, 2);
//		System.out.println(c);
		
//		commentService.updateComment("한글 korean",5);
		
		
//		List<Comment> list = commentService.selectCommentListByBoardNo(2);
//		
//		for(Comment c2: list){
//			System.out.println(c2+"");
//		}
		
		System.out.println(commentService.selectCommentCountByBoardNo(2));
	}
	
	
}
