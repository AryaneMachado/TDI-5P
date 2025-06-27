<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="base-head.jsp" %>
    <title>CRUD Manager - Cursos</title>
</head>
<body>
    <%@ include file="modal.html" %>
    <%@ include file="nav-menu.jsp" %>
    
    <div id="container" class="container-fluid">
        <!-- Alert -->
        <div id="alert"
             style="${not empty message ? 'display: block;' : 'display: none;'}"
             class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${message}
        </div>
        
        <!-- Top bar: título, pesquisa e botão de novo -->
        <div id="top" class="row">
            <div class="col-md-3">
                <h3>Cursos</h3>
            </div>
            <div class="col-md-6">
                <div class="input-group h2">
                    <input name="data[search]" class="form-control" id="search" type="text"
                           placeholder="Pesquisar cursos">
                    <span class="input-group-btn">
                        <button class="btn btn-danger" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/course/form"
                   class="btn btn-danger pull-right h2">
                    <span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Curso
                </a>
            </div>
        </div>
        
        <hr/>
        
        <!-- Lista de cursos -->
        <div id="list" class="row">
            <div class="table-responsive col-md-12">
                <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
                    <thead>
                        <tr>
                            <th>Modalidade</th>
                            <th>Nome</th>
                            <th>Data Início</th>
                            <th>Data Término</th>
                            <th>Usuário</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td>${course.modality}</td>
                                <td>${course.name}</td>
                                <td>${course.startDate}</td>
                                <td>${course.endDate}</td>
                                <td>${course.user.name}</td>
                                
                                <!-- Editar -->
                                <td class="actions">
                                    <a class="btn btn-info btn-xs"
                                       href="${pageContext.request.contextPath}/course/update?id=${course.id}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                
                                <!-- Excluir (usa modal) -->
                                <td class="actions">
                                    <a class="btn btn-danger btn-xs modal-remove"
                                       course-id="${course.id}"
                                       course-name="${course.name}"
                                       data-toggle="modal"
                                       data-target="#delete-modal"
                                       href="#">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Paginação (estática; ajuste se for implementar) -->
        <div id="bottom" class="row">
            <div class="col-md-12">
                <ul class="pagination">
                    <li class="disabled"><a>&lt; Anterior</a></li>
                    <li class="disabled"><a>1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
                </ul>
            </div>
        </div>
    </div>
    
    <!-- scripts -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            // fecha o alert após 3 segundos
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);
            
            // prepara o modal de deleção
            $(".modal-remove").click(function () {
                var courseName = $(this).attr('course-name');
                var courseId   = $(this).attr('course-id');
                // texto dentro do modal
                $(".modal-body #hiddenValue").text("o curso '" + courseName + "'");
                // preenche o hidden field do form do modal
                $("#id").attr("value", courseId);
                // ajusta a action do form para apontar ao delete de curso
                $("#form").attr("action", "course/delete");
            });
        });
    </script>
</body>
</html>
