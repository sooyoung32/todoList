package controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String loginForm() {
		return "login_form";
	}
	
	//회원 여부 확인 Ajax
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public @ResponseBody String login(Member member){
		String result=null;
		try {
			result = service.loginCheck(member.getEmail(), member.getPassword());
			System.out.println("login.do/result: "+result);
		} catch (UnknownHostException e) {
			System.out.println("컨트롤러 loginCheck 에러 : "+e);
		}
		return result; 
	}
	
	//로그인 성공
	@RequestMapping(value="loginSuccess.do", method=RequestMethod.POST)
	public ModelAndView loginSuccess(String email, HttpServletRequest request){
		Member member = service.selectMember(email); 
		ModelAndView mv = new ModelAndView("redirect:boardList.do?page=1"); 
		mv.addObject("member", member);
		HttpSession session = request.getSession(true);
		session.setAttribute("email", member.getEmail());
		session.setAttribute("name", member.getName());
		System.out.println("로그인 세션 확보!");
		return mv; 
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
	@RequestMapping(value="joinSuccess.do", method=RequestMethod.POST)
	public String join(Member member, HttpServletRequest request) {
		service.insertMember(member);
		return "board_list";
	}

}
