<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp" %>
</head>
<body>
    <%@include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <h3 class="page-header">Lista de Requisições</h3>
            </div>
            <div class="col-md-6 text-right">
                <br />
                <a href="${pageContext.request.contextPath}/requisicao/form" class="btn btn-primary">
                    Nova Requisição
                </a>
            </div>
        </div>

        <c:if test="${empty requisicoes}">
            <div class="alert alert-info">Nenhuma requisição encontrada.</div>
        </c:if>

        <c:if test="${not empty requisicoes}">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Quantidade</th>
                        <th>Data</th>
                        <th>Status</th>
                        <th>Usuário</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="req" items="${requisicao}">
                        <tr>
                            <td>${req.getId()}</td>
                            <td>${req.getDescricao()}</td>
                            <td>${req.getQuantidade()}</td>
                            <td>${req.getDataRequisicao()}</td>
                            <td>${req.getStatus()}</td>
                            <td>${req.getUsuario().getName()}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/requisicao/form?id=${req.getId()}"
                                   class="btn btn-xs btn-warning">Editar</a>

                                <a href="${pageContext.request.contextPath}/requisicao/delete?id=${req.getId()}"
                                   class="btn btn-xs btn-danger"
                                   onclick="return confirm('Deseja realmente excluir esta requisição?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
