package controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
	private Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	private MemberService service;

	// �α���ȭ��
	@RequestMapping(value = "loginForm.do")
	public ModelAndView loginForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login_form");
		String preAddr = request.getHeader("REFERER");
		mv.addObject("preAddr", preAddr);
		return mv;
	}
	
	@RequestMapping(value = "ajaxLoginCheck.do")
	@ResponseBody
	public String ajaxLoginCheck(HttpServletRequest request) {

		String isAjax = (String) request.getAttribute("result");
		logger.debug("[ajaxLoginCheck] " + "isAjax // " + isAjax);
		String result = null;
		if ("E".equals(isAjax)) {
			result = "E";
			logger.debug("[ajaxLoginCheck result ] " + result);
			return result;
		} else {
			result = "GO_TO";
			logger.debug("[ajaxLoginCheck result ] " + result);
			return result;
		}
	}
	
	// ȸ�� ���� Ȯ�� Ajax
	@RequestMapping(value = "login.do")
	public @ResponseBody String login(Member member, HttpServletRequest request) {
		System.err.println(member);
		
		String result = null;
		try {
			result = service.loginCheck(member.getEmail(), member.getPassword());

			String preAddr = request.getParameter("preAddr");
			boolean preAddrCheck = preAddr.indexOf("boardList.do") >= 0;
			logger.debug("[������������ �Ѿ�� �����ּ�] : " + preAddr);
			logger.debug("[�����ּҰ� boardList.do���� �°� Ȯ��] : " + preAddrCheck);
			
			//preAddrCheck�� true�̸� ���帮��Ʈȭ������ (�۾���, ��� ��)
			if (result == "success" && preAddrCheck) {
				getSession(member, request);
				return "success";
			}//preAddrCheck�� false�� ���� ȭ�� ���ε�(��� ���� ���� ��) 
				else if (result == "success" && !preAddrCheck) {
				getSession(member, request);
				return "success2";
			}
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		return "";
	}

	
	private void getSession(Member member, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Member member2 = service.selectMember(member.getEmail());
		session.setAttribute("email", member2.getEmail());
		session.setAttribute("name", member2.getName());
		logger.debug(session.getAttribute("email") + "email");
		logger.debug(session.getAttribute("name") + "name");
		logger.debug("�α��� ���� Ȯ��!");
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		logger.debug("�α׾ƿ�! ���Ǻ���");
		return "redirect:boardList.do?page=1";
	}

	// ȸ������

	// ȸ������ ȭ��
	@RequestMapping(value = "joinForm.do")
	public String joinForm() {
		return "join_form";
	}

	// ȸ�� �̸��� �ߺ� üũ
	@RequestMapping(value = "dupCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public String dupCheck(String email) {
		logger.debug("test : dupCheck");
		if (service.selectMember(email) != null) {
			return "unusable";
		} else {
			return "usable";
		}
	}

	// ȸ������ ����
	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	@ResponseBody
	public String join(String name, String password, String email, HttpServletRequest request) {
		Member member = new Member();
		member.setEmail(email);
		member.setPassword(password);
		member.setName(name);
		System.out.println("member//" + member);
		int result = service.joinMember(member);
		logger.debug ("member//" + member);
		if (result == 1) {
			return "Y";
		} else {
			return "N";
		}
	}

}