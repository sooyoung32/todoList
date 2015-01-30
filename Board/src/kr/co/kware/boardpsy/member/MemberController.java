package kr.co.kware.boardpsy.member;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		String preAddr = request.getHeader("REFERER"); // 인터셉터위해 이전 url 알기
		mv.addObject("preAddr", preAddr);
		return mv;
	}

	// ajax 데이터 값 map을 통해 여러개 갈 수 있는것을 보여주기위해.
	@RequestMapping(value = "ajaxUpdateLoginCheck.do")
	public @ResponseBody
	Map<Object, Object> ajaxUpdateLoginCheck(int boardNo, HttpServletRequest request) {
		String isAjax = (String) request.getAttribute("result");
		Map<Object, Object> map = new HashMap<Object, Object>();

		logger.debug("업데이트에서 에이젝스 값 ////////////////" + isAjax);
		if (!"E".equals(isAjax)) {
			map.put("boardNo", boardNo);
		} else {
			map.put("isAjax", "E");
			logger.debug("map : " + map);
		}
		return map;
	}

	// writeForm, write,reply,replyForm 에이젝스로 인터셉터 처리.
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
		boolean preAddrCheck = preAddr.indexOf("boardList.do") >= 0;
		logger.debug("[에이젝스에서 넘어온 이전주소] : " + preAddr);
		logger.debug("[이전주소가 boardList.do에서 온거 확인] : " + preAddrCheck);

		// preAddrCheck이 true이면 보드리스트화면으로 (글쓰기, 답글 등)
		if (result == "noID"){
			return "noID";
		}else if (result =="noPW"){
			return "noPW";
		}else if (result == "success" && preAddrCheck) {
			setSession(member, request);
			return "success";
		}// preAddrCheck이 false면 현재 화면 리로드(댓글 수정 삭제 등)
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
		//TODO setSession 할때 member 자체를 담는다. 
		session.setMaxInactiveInterval(60 * 60);
		logger.debug(session.getAttribute("email") + "email");
		logger.debug(session.getAttribute("name") + "name");
		logger.debug("로그인 세션 확보!");
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("name");
		// session.invalidate();
		logger.debug("로그아웃! 세션빠잉");
		return "redirect:boardList.do?page=1";
	}

	// 회원가입

	// 회원가입 화면
	@RequestMapping(value = "joinForm.do")
	public String joinForm() {
		return "join_form";
	}

	// 회원 이메일 중복 체크
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

	// 회원가입 성공
	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	@ResponseBody
	public String join(String name, String password, String email, HttpServletRequest request) {
		Member member = new Member();
		member.setEmail(email);
		member.setPassword(password);
		member.setName(name);
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
		logger.debug("페북 이메일로 찾은 멤버 : " + member);
		Member newFbMember = new Member();
		
		if (member == null) {   //FB계정 이메일이 Kware멤버에 존재하지 않을때
			newFbMember.setFbToken(fbToken);
			newFbMember.setFbUserId(fbUserId);
			newFbMember.setName(name);
			newFbMember.setIsFB("Y");
			newFbMember.setEmail(email);
			// newFbMember.setPassword("S");
			newFbMember.setLoginDate(new Date());
			memberServiceImpl.joinMember(newFbMember);
		} else {									//Kware 멤버에 존재할때
 			member.setFbToken(fbToken);
			member.setFbUserId(fbUserId);
			member.setIsFB("Y");
			member.setLoginDate(new Date());
			logger.debug("멤버 비밀번호 : " + member.getPassword());
			memberServiceImpl.modifyMember(member, member.getPassword());
		}

		HttpSession session = request.getSession();
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		session.setAttribute("userId", member.getFbUserId());
		logger.debug("세션 받나..? 페북 유저 아이디: " + session.getAttribute("email"));
		return "success";
	}

	@RequestMapping(value = "fbLogout.do")
	@ResponseBody
	public String fbLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("name");
		session.removeAttribute("userId");
		logger.debug("여기오는건가.. " + request.getSession());
		return "logout";
	}

}