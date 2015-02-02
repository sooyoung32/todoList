package kr.co.kware.common.web.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginIntercepter extends HandlerInterceptorAdapter {
	
	private Logger logger = Logger.getLogger(LoginIntercepter.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		try {
			boolean isAjax = "Y".equals(request.getParameter("ajaxYn"));
			HttpSession session = request.getSession();

			if (!isAjax && session.getAttribute("email") == null) {
				response.sendRedirect("/Board_psy/boardList.do");
				return false;
			} else if (isAjax && session.getAttribute("email") == null) {
				request.setAttribute("result", "E");
			}

		} catch (Exception e) {
			logger.error("인터셉터 에러 : " +e);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
