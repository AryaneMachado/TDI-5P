package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String uri = request.getRequestURI();
		boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("login");
		boolean isStaticResource = uri.contains("/images") || uri.contains("/css") || uri.contains("/js");

		HttpSession session = request.getSession(false);
		boolean loggedIn = (session != null && session.getAttribute("loggedUser") != null);

		if (loggedIn || isLoginPage || isStaticResource) {
			chain.doFilter(req, res);
		} else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
}
