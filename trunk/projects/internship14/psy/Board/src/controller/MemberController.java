package controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.MemberService;
import vo.Member;

@Controller
public class MemberController {

	MemberService service;

	@Autowired
	public void setService(MemberService service) {
		this.service = service;
	}
	//////////////////////////////////////////////////////////////////////////
	
	//로그인
	//로그인화면
	@RequestMapping(value="loginForm.do")
	public ModelAndView loginForm(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("login_form");
		String preAddr = request.getHeader("REFERER");
		System.out.println("[로그인폼컨트롤러 이전주소] : "+preAddr);
		mv.addObject("preAddr", preAddr);
		return mv;
	}
		
	//회원 여부 확인 Ajax
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public @ResponseBody String login(Member member,HttpServletRequest request){
		String result=null;
		try {
			result = service.loginCheck(member.getEmail(), member.getPassword());
			
			
			String preAddr = request.getParameter("preAddr");
			boolean preAddrCheck = "http://localhost:9088/Board_psy/boardList.do?page=1".equals(preAddr);
			System.out.println("[에이젝스에서 넘어온 이전주소] : "+ preAddr);
			System.out.println("[이전주소가 boardList.do에서 온거 확인] : "+preAddrCheck);
			
			
			if(result=="success" && preAddrCheck){
				HttpSession session = request.getSession(true);
				Member member2 = service.selectMember(member.getEmail());
				session.setAttribute("email", member2.getEmail());
				session.setAttribute("name", member2.getName());
				System.out.println(session.getAttribute("email") +"email");
				System.out.println(session.getAttribute("name") +"name");
				System.out.println("로그인 세션 확보!");
				return "success";
			}else if (result=="success" && !preAddrCheck){
				HttpSession session = request.getSession(true);
				Member member2 = service.selectMember(member.getEmail());
				session.setAttribute("email", member2.getEmail());
				session.setAttribute("name", member2.getName());
				System.out.println(session.getAttribute("email") +"email");
				System.out.println(session.getAttribute("name") +"name");
				System.out.println("로그인 세션 확보!");
				return "success2";
			}
		} catch (UnknownHostException e) {
			System.out.println("컨트롤러 loginCheck 에러 : "+e);
		}
		return ""; 
	}
	
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("로그아웃! 세션빠잉");
		return "redirect:boardList.do?page=1";
	}
	
	
	//회원가입
	
	//회원가입 화면
	@RequestMapping(value="joinForm.do")
	public String joinForm() {
		System.out.println("Test");
		return "join_form";
	}
	
	
	//회원 이메일 중복 체크
	@RequestMapping(value="dupCheck.do", method=RequestMethod.POST)
	@ResponseBody
	public String dupCheck(String email){
		System.out.println("test : dupCheck");
		if(service.selectMember(email)!=null){
			return "unusable";
		}else{
			return "usable"; 
		}
	}
	
	
	//회원가입 성공
	@RequestMapping(value="join.do", method=RequestMethod.POST)
	@ResponseBody
	public String join(String name, String password, String email, HttpServletRequest request) {
		Member member = new Member(); 
		member.setEmail(email);
		member.setPassword(password);
		member.setName(name);
		System.out.println("member//"+member);
		int result = service.insertMember(member);
		 System.out.println("member//"+member);
		 if(result ==1){
			 return "Y";
		 }else{
			 return "N";
		 }
	}

}
