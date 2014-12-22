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
	
	//�α���
	//�α���ȭ��
	@RequestMapping(value="loginForm.do")
	public String loginForm() {
		return "login_form";
	}
	
	//ȸ�� ���� Ȯ�� Ajax
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	@ResponseBody
	public String login(Member member){
		String result=null;
		try {
			result = service.loginCheck(member.getEmail(), member.getPassword());
			System.out.println("login.do/result: "+result);
		} catch (UnknownHostException e) {
			System.out.println("��Ʈ�ѷ� loginCheck ���� : "+e);
		}
		return result; 
	}
	
	//�α��� ����
	@RequestMapping(value="loginSuccess.do", method=RequestMethod.POST)
	public ModelAndView loginSuccess(String email, HttpServletRequest request){
		Member member = service.selectMember(email); 
		ModelAndView mv = new ModelAndView("board_list"); 
		mv.addObject("member", member);
		HttpSession session = request.getSession(true);
		session.setAttribute("email", member.getEmail());
		session.setAttribute("name", member.getName());
		System.out.println("�α��� ���� Ȯ��!");
		return mv; 
	}
	
	
	//ȸ������
	
	//ȸ������ ȭ��
	@RequestMapping(value="joinForm.do")
	public String joinForm() {
		System.out.println("Test");
		return "join_form";
	}
	
	
	//ȸ�� �̸��� �ߺ� üũ
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
	
	
	//ȸ������ ����
	@RequestMapping(value="join.do", method=RequestMethod.POST)
	public String join(Member member, HttpServletRequest request) {
		service.insertMember(member);
		return "board_list";
	}

}
