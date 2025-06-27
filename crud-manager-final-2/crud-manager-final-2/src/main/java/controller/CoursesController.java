package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.ModelException;
import model.User;
import model.dao.CourseDAO;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {
    "/courses", 
    "/course/form", 
    "/course/delete", 
    "/course/insert", 
    "/course/update"
})
public class CoursesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/crud-manager/course/form": {
                CommonsController.listUsers(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-course.jsp");
                break;
            }
            case "/crud-manager/course/update": {
                CommonsController.listUsers(req);
                Course c = loadCourse(req);
                req.setAttribute("course", c);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-course.jsp");
                break;
            }
            default: {
                listCourses(req);
                ControllerUtil.transferSessionMessagesToRequest(req);
                ControllerUtil.forward(req, resp, "/courses.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/crud-manager/course/delete":
                deleteCourse(req, resp);
                break;
            case "/crud-manager/course/insert":
                insertCourse(req, resp);
                break;
            case "/crud-manager/course/update":
                updateCourse(req, resp);
                break;
            default:
                System.err.println("URL inválida: " + action);
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/courses");
    }

    private Course loadCourse(HttpServletRequest req) {
        String idParam = req.getParameter("id");
        int courseId = Integer.parseInt(idParam);

        CourseDAO dao = DAOFactory.createDAO(CourseDAO.class);
        try {
            Course c = dao.findById(courseId);
            if (c == null) {
                throw new ModelException("Curso não encontrado para alteração");
            }
            return c;
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
            return null;
        }
    }

    private void insertCourse(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String modality = req.getParameter("modality");
            String name     = req.getParameter("name");
            Date startDate  = new SimpleDateFormat("yyyy-MM-dd")
                                  .parse(req.getParameter("startDate"));
            Date endDate    = new SimpleDateFormat("yyyy-MM-dd")
                                  .parse(req.getParameter("endDate"));
            int userId      = Integer.parseInt(req.getParameter("user"));

            Course c = new Course();
            c.setModality(modality);
            c.setName(name);
            c.setStartDate(startDate);
            c.setEndDate(endDate);
            c.setUser(new User(userId));

            CourseDAO dao = DAOFactory.createDAO(CourseDAO.class);
            boolean ok = dao.save(c);
            if (ok) {
                ControllerUtil.sucessMessage(req, 
                  "Curso '" + c.getName() + "' inserido com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, 
                  "Não foi possível inserir o curso '" + c.getName() + "'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, 
              "Erro ao inserir curso: " + e.getMessage());
        }
    }

    private void updateCourse(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Course c = loadCourse(req);
            if (c == null) return;

            String modality = req.getParameter("modality");
            String name     = req.getParameter("name");
            Date startDate  = new SimpleDateFormat("yyyy-MM-dd")
                                  .parse(req.getParameter("startDate"));
            Date endDate    = new SimpleDateFormat("yyyy-MM-dd")
                                  .parse(req.getParameter("endDate"));
            int userId      = Integer.parseInt(req.getParameter("user"));

            c.setModality(modality);
            c.setName(name);
            c.setStartDate(startDate);
            c.setEndDate(endDate);
            c.setUser(new User(userId));

            CourseDAO dao = DAOFactory.createDAO(CourseDAO.class);
            boolean ok = dao.update(c);
            if (ok) {
                ControllerUtil.sucessMessage(req, 
                  "Curso '" + c.getName() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, 
                  "Não foi possível atualizar o curso '" + c.getName() + "'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, 
              "Erro ao atualizar curso: " + e.getMessage());
        }
    }

    private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Course c = loadCourse(req);
            if (c == null) return;

            CourseDAO dao = DAOFactory.createDAO(CourseDAO.class);
            boolean ok = dao.delete(c);
            if (ok) {
                ControllerUtil.sucessMessage(req, 
                  "Curso '" + c.getName() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, 
                  "Não foi possível deletar o curso '" + c.getName() + "'.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, 
              "Erro ao deletar curso: " + e.getMessage());
        }
    }

    private void listCourses(HttpServletRequest req) {
        CourseDAO dao = DAOFactory.createDAO(CourseDAO.class);
        try {
            List<Course> list = dao.listAll();
            req.setAttribute("courses", list);
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, 
              "Erro ao listar cursos: " + e.getMessage());
        }
    }
}
