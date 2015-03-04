package kr.co.kware.member.controller;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kware.member.service.MemberService;
import kr.co.kware.member.vo.Member;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	private Logger logger = Logger.getLogger(MemberController.class);

	@Autowired
	private MemberService memberServiceImpl;

	@RequestMapping(value = "loginForm.do")
	public ModelAndView loginForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login_form");
		String preAddr = request.getHeader("REFERER"); // ���ͼ������� ���� url �˱�
		mv.addObject("preAddr", preAddr);
		return mv;
	}

	// ajax ������ �� map�� ���� ������ �� �� �ִ°��� �����ֱ�����.
	@RequestMapping(value = "ajaxUpdateLoginCheck.do")
	public @ResponseBody
	Map<Object, Object> ajaxUpdateLoginCheck(int articleNo, HttpServletRequest request) {
		String isAjax = (String) request.getAttribute("result");
		Map<Object, Object> map = new HashMap<Object, Object>();

		logger.debug("������Ʈ���� �������� �� ////////////////" + isAjax);
		if (!"E".equals(isAjax)) {
			map.put("articleNo", articleNo);
		} else {
			map.put("isAjax", "E");
			logger.debug("map : " + map);
		}
		return map;
	}

	// writeForm, write,reply,replyForm ���������� ���ͼ��� ó��.
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

	@RequestMapping(value = "login.do")
	public @ResponseBody
	String login(Member member, HttpServletRequest request) {
		System.err.println(member);

		String result = null;
		result = memberServiceImpl.loginCheck(member.getEmail(), member.getPassword());

		String preAddr = request.getParameter("preAddr");
		boolean preAddrCheck = preAddr.indexOf("articleList.do") >= 0;
		logger.debug("[������������ �Ѿ�� �����ּ�] : " + preAddr);
		logger.debug("[�����ּҰ� articleList.do���� �°� Ȯ��] : " + preAddrCheck);

		// preAddrCheck�� true�̸� ���帮��Ʈȭ������ (�۾���, ��� ��)
		if (result == "noID"){
			return "noID";
		}else if (result =="noPW"){
			return "noPW";
		}else if (result == "success" && preAddrCheck) {
			setSession(member, request);
			return "success";
		}// preAddrCheck�� false�� ���� ȭ�� ���ε�(��� ���� ���� ��)
		  else if (result == "success" && !preAddrCheck) {
			setSession(member, request);
			return "success2";
		}
		return "";
	}

	private void setSession(Member member, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Member member2 = memberServiceImpl.getMemberbyEmail(member.getEmail());
		session.setAttribute("email", member2.getEmail());
		session.setAttribute("name", member2.getName());
		//TODO setSession �Ҷ� member ��ü�� ��´�. 
		session.setMaxInactiveInterval(60 * 60);
		logger.debug(session.getAttribute("email") + "email");
		logger.debug(session.getAttribute("name") + "name");
		logger.debug("�α��� ���� Ȯ��!");
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("name");
		// session.invalidate();
		logger.debug("�α׾ƿ�! ���Ǻ���");
		return "redirect:articleList.do?page=1";
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
		if (memberServiceImpl.getMemberbyEmail(email) != null) {
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
		member.setWritingIP(getClientIP(request));
		member.setIsFB("N");
		System.out.println("member//" + member);
		int result = memberServiceImpl.joinMember(member);
		logger.debug("member//" + member);
		if (result == 1) {
			return "Y";
		} else {
			return "N";
		}
	}

	@RequestMapping(value = "fbLogin.do")
	@ResponseBody
	public String fbLogin(String fbToken, String fbUserId, String name, String email, HttpServletRequest request)
			throws UnknownHostException {

		Member member = memberServiceImpl.getMemberbyEmail(email);
		logger.debug("��� �̸��Ϸ� ã�� ��� : " + member);
		Member newFbMember = new Member();
		
		if (member == null) {   //FB���� �̸����� Kware����� �������� ������
			newFbMember.setFbToken(fbToken);
			newFbMember.setFbUserId(fbUserId);
			newFbMember.setName(name);
			newFbMember.setIsFB("Y");
			newFbMember.setEmail(email);
			// newFbMember.setPassword("S");
			newFbMember.setLoginDate(new Date());
			memberServiceImpl.joinMember(newFbMember);
		} else {									//Kware ����� �����Ҷ�
 			member.setFbToken(fbToken);
			member.setFbUserId(fbUserId);
			member.setIsFB("Y");
			member.setLoginDate(new Date());
			member.setModifyIP(getClientIP(request));
			logger.debug("��� ��й�ȣ : " + member.getPassword());
			memberServiceImpl.modifyMember(member, member.getPassword());
		}

		HttpSession session = request.getSession();
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		session.setAttribute("userId", member.getFbUserId());
		logger.debug("���� �޳�..? ��� ���� ���̵�: " + session.getAttribute("email"));
		return "success";
	}

	@RequestMapping(value = "fbLogout.do")
	@ResponseBody
	public String fbLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("name");
		session.removeAttribute("userId");
		logger.debug("������°ǰ�.. " + request.getSession());
		return "logout";
	}
	
	public String getClientIP(HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // ������
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		
		return ip;

	}

}