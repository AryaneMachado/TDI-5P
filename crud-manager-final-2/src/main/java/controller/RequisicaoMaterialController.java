package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import model.dao.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/requisicao")
public class RequisicaoMaterialController extends HttpServlet {

    private RequisicaoMaterialDAO dao;
    private UserDAO userDAO;

    @Override
    public void init() {
        dao = DAOFactory.createDAO(RequisicaoMaterialDAO.class);
        userDAO = DAOFactory.createDAO(UserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                list(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        RequisicaoMaterial r = new RequisicaoMaterial();
        r.setDescricao(request.getParameter("descricao"));
        r.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        r.setDataRequisicao(java.sql.Date.valueOf(request.getParameter("dataRequisicao")));
        r.setStatus(request.getParameter("status"));

        User user = userDAO.findById(Integer.parseInt(request.getParameter("usuarioId")));
        r.setUsuario(user);

        if (id == null || id.isEmpty()) {
            dao.insert(r);
        } else {
            r.setId(Integer.parseInt(id));
            dao.update(r);
        }

        response.sendRedirect("requisicao");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RequisicaoMaterial> list = dao.findAll();
        request.setAttribute("lista", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("requisicao-lista.jsp");
        dispatcher.forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> usuarios = userDAO.findAll();
        request.setAttribute("usuarios", usuarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("requisicao-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RequisicaoMaterial r = dao.findById(id);
        List<User> usuarios = userDAO.findAll();
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("requisicao", r);
        RequestDispatcher dispatcher = request.getRequestDispatcher("requisicao-form.jsp");
        dispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(id);
        response.sendRedirect("requisicao");
    }
}
