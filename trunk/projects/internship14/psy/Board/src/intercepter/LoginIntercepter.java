package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		try {
			boolean isAjax = "Y".equals(request.getParameter("ajaxYn"));
			System.out.println("[인터셉터 isAjax 값 Y 확인]:"+isAjax);
			HttpSession session = request.getSession();
			
			if (!isAjax && session.getAttribute("email") == null) {
				System.out.println("에이젝스 아니고 세션 없다");
				response.sendRedirect("/Board_psy/boardList.do");
				return false;
			}else if (isAjax && session.getAttribute("email") == null){
				request.setAttribute("result", "E" );
			}

		} catch (Exception e) {
			System.out.println("로그인 인터셉터 에러 // " + e);
		}

		System.out.println("로그인 확인");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		boolean isAjax = "Y".equals(request.getParameter("ajaxYn"));
		System.out.println("Ajax??  :" + isAjax);

		HttpSession session = request.getSession();
		if (isAjax && session.getAttribute("email") == null) {
			System.out.println("에이젝스 + 세션 없다 ");
//			response.sendRedirect("/Board_psy/boardList.do");
//			request.setAttribute("result", "E");
			System.out.println("포스트 핸들러");
		}

	}

}
