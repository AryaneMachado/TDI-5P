package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.ModelException;
import model.Post;
import model.User;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Servlet implementation class PostsController
 */
@WebServlet({ "/posts", "/posts/save", "/posts/edit", "/posts/update", "/posts/delete" })
public class PostsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		System.out.println("Action GET = " + action);
		switch (action) {
		case "/facebook/posts":
			loadPosts(req);
			req.getRequestDispatcher("posts.jsp").forward(req, resp);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		System.out.println("Action POST = " + action);
		switch (action) {
		case "/facebook/posts/save": {
			if(req.getParameter("post_id") == "") {
			System.out.println("entrou no create");
			insertPost(createPost(req));
			}else {
				System.out.println("entrou no update");
				updatePost(createPost(req),req);
			}
			resp.sendRedirect("/facebook/posts");
			break;
		}
		case "/facebook/posts/edit": {
			editPost(req);
			req.getRequestDispatcher("/form_post.jsp").forward(req, resp);
			break;
		}
		case "/facebook/posts/delete": {
			delete(req);
			resp.sendRedirect("/facebook/posts");
			break;
		}
		}
	}
	
	private void insertPost(Post post) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			dao.save(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
	private void updatePost(Post post, HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			System.out.println(dao.update(post));
			req.setAttribute("post_id", "");
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		List<Post> posts = null;

		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (posts != null) {
			req.setAttribute("posts", posts);
		}
	}

	private void editPost(HttpServletRequest req) {
		Integer idPost = Integer.parseInt(req.getParameter("post_update_id"));
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			Post post = dao.findById(idPost);
			req.setAttribute("post", post);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Post createPost(HttpServletRequest req) {

		Post post = new Post();
		try {
			PostDAO daoPost = DAOFactory.createDAO(PostDAO.class);
			UserDAO daoUser = DAOFactory.createDAO(UserDAO.class);
			if(req.getParameter("post_id")!= "" ) {
				post = daoPost.findById(Integer.parseInt(req.getParameter("post_id")));
			}
			post.setContent(req.getParameter("content"));
			Date postDate = new Date();
			post.setPostDate(postDate);
			User user = daoUser.findById(Integer.parseInt(req.getParameter("user_post_id")));
			post.setUser(user);
			
		} catch (ModelException e) {
			e.printStackTrace();
		}
		return post;

	}
	
	private void delete(HttpServletRequest req) {
    	PostDAO dao = DAOFactory.createDAO(PostDAO.class);
    	
    	try {
			Post postDelete = dao.findById(Integer.parseInt(req.getParameter("post_delete_id")));
			System.out.println("result delete " + dao.delete(postDelete));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
