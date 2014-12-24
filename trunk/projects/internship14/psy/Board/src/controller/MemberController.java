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
	public @ResponseBody String login(Member member,HttpServletRequest request){
		String result=null;
		try {
			result = service.loginCheck(member.getEmail(), member.getPassword());
			
			if(result=="success"){
				HttpSession session = request.getSession(true);
				Member member2 = service.selectMember(member.getEmail());
				session.setAttribute("email", member2.getEmail());
				session.setAttribute("name", member2.getName());
				System.out.println(session.getAttribute("email") +"email");
				System.out.println(session.getAttribute("name") +"name");
				System.out.println("�α��� ���� Ȯ��!");
			}
			System.out.println("login.do/result: "+result);
		} catch (UnknownHostException e) {
			System.out.println("��Ʈ�ѷ� loginCheck ���� : "+e);
		}
		return result; 
	}
	
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("�α׾ƿ�! ���Ǻ���");
		return "redirect:boardList.do?page=1";
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
	@RequestMapping(value="joinSuccess.do", method=RequestMethod.POST)
	public String join(Member member, HttpServletRequest request) {
		service.insertMember(member);
		System.out.println(member.getEmail() +"ȸ������ �Ƴ�???");
		return "join_success";
	}

}
