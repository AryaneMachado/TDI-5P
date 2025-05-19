package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/headers", "/request-body", "/session", "/status", "/methods"})
public class JavaServletsController extends HttpServlet {
	
	/*GET /headers mostra cabeçalhos da requisição
POST /request-body mostra o corpo da requisição recebida (via html form)
GET /session manipulação de sessão e cookies
GET /status?code=404 Responde com o código de status indicado
OPTIONS /methods responde com os métodos aceitos
*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath(); // Verificar melhor aqui

        switch (path) {
            case "/headers":
                processHeaders(request, response);
                break;
            case "/session":
                processSession(request, response);
                break;
            case "/status":
                processStatus(request, response);
                break;
            default:
                response.sendError(404, "Endereço GET não suportado. Use /headers, /session ou /status.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath(); // Verificar melhor aqui

        if ("/request-body".equals(path)) {
            processRequestBody(request, response);
        } else {
            response.sendError(404, "Endereço POST não suportado");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath(); // /methods

        if ("/methods".equals(path)) {
            response.setHeader("Allow", "GET, POST, PUT, DELETE, OPTIONS");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/plain");
            response.getWriter().println("Métodos aceitos: GET, POST, PUT, DELETE, OPTIONS");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Caminho OPTIONS não suportado");
        }
    }

    // Chamando OPTIONS

    private void processHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        out.println("Cabeçalhos da Requisição:");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            out.println(name + ": " + request.getHeader(name));
        }

        out.println("\nIP: " + request.getRemoteAddr());
        out.println("Versão HTTP: " + request.getProtocol());
    }

    
    // Chamando POSTS

    private void processRequestBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String mensagem = request.getParameter("mensagem");

        out.println("Nome: " + nome);
        out.println("Email: " + email);
        out.println("Mensagem: " + mensagem);

        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            out.println(line);
        }
    }
    
    
    // Chamando GET

    private void processSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String nome = "X";
        boolean novo = true;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("usuario".equals(c.getName())) {
                    novo = false;
                    nome = c.getValue();
                    break;
                }
            }
        }

        if (novo) {
            Cookie novoCookie = new Cookie("usuario", nome);
            novoCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(novoCookie);
            out.println("<h1>Olá, usuário " + nome + "! Cookie criado.</h1>");
        } else {
            out.println("<h1>Bem-vindo de volta, usuário " + nome + "!</h1>");
        }
    }

    private void processStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeParam = request.getParameter("code");
        int code;

        try {
            code = Integer.parseInt(codeParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Código inválido.");
            return;
        }

        response.setStatus(code);
        response.getWriter().println("Status definido como: " + code);
    }
}
