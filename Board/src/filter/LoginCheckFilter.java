package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		httpRequest.setCharacterEncoding("UTF-8");
		
		boolean login = false;
		
		if (session != null) {
			if (session.getAttribute("email") != null && session.getAttribute("password") != null) {
				login = true; // ���Ǻ����� null�� �ƴҰ�� true�� ����.
			}
		}

		if (login) {
			// ���Ǻ����� null�� �ƴҰ��, ���� ü���� ��ģ ��, ��û�� �������� �̵��Ѵ�.
			chain.doFilter(request, response);
		} else {
			// ���Ǻ����� null�� ���, �α��� ������ �̵��Ѵ�.
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginForm.do");
			dispatcher.forward(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
