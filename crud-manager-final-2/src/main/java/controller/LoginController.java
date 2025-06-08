package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.entities.User;

@WebServlet("/logincontroller")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
		User user = userDAO.findByEmailAndPassword(email, password);

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loggedUser", user);
			response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("errorMessage", "E-mail ou senha inválidos.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
