package service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vo.Member;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml"})
public class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@BeforeClass
	public static void setUpClass() {
		
	}
	
	@Before
	public void setUp() {
		memberService = new MemberService();
	}

	@Test
	public void testJoinMember() {
		Member member = new Member();
		assertEquals(1, memberService.joinMember(member));
	}

	@Test
	public void testUpdateMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectMemberCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectMemberList() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoginCheck() {
		fail("Not yet implemented");
	}

}
