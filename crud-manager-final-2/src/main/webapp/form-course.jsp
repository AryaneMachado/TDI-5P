<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="base-head.jsp" %>
</head>
<body>
    <%@ include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">
        <h3 class="page-header">${not empty course ? 'Atualizar' : 'Adicionar'} Curso</h3>

        <form action="${pageContext.request.contextPath}/course/${action}" method="POST">
            <input type="hidden" name="id" value="${course.id}" />

            <div class="row">
				<div class="form-group col-md-6">
					<label for="modality">Modalidade</label> <select id="modality"
						name="modality" class="form-control" required
						oninvalid="this.setCustomValidity('Por favor, selecione a modalidade.')"
						oninput="setCustomValidity('')">
						<option value="" ${empty course.modality ? 'selected' : ''}>Selecione
							a modalidade</option>
						<option value="Presencial"
							${course.modality == 'Presencial' ? 'selected' : ''}>
							Presencial</option>
						<option value="Híbrido"
							${course.modality == 'Híbrido' ? 'selected' : ''}>
							Híbrido</option>
						<option value="EAD" ${course.modality == 'EAD' ? 'selected' : ''}>
							EAD</option>
					</select>
				</div>

				<div class="form-group col-md-6">
                    <label for="name">Nome do Curso</label>
                    <input type="text" class="form-control" id="name" name="name"
                           placeholder="Informe o nome do curso"
                           required
                           oninvalid="this.setCustomValidity('Por favor, informe o nome do curso.')"
                           oninput="setCustomValidity('')"
                           value="${course.name}" />
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="startDate">Data de Início</label>
                    <input type="date" class="form-control" id="startDate" name="startDate"
                           required
                           oninvalid="this.setCustomValidity('Por favor, informe a data de início.')"
                           oninput="setCustomValidity('')"
                           value="${course.startDate}" />
                </div>

                <div class="form-group col-md-6">
                    <label for="endDate">Data de Término</label>
                    <input type="date" class="form-control" id="endDate" name="endDate"
                           required
                           oninvalid="this.setCustomValidity('Por favor, informe a data de término.')"
                           oninput="setCustomValidity('')"
                           value="${course.endDate}" />
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="user">Usuário</label>
                    <select id="user" class="form-control selectpicker" name="user"
                            required
                            oninvalid="this.setCustomValidity('Por favor, selecione um usuário.')"
                            oninput="setCustomValidity('')">
                        <option value="" ${empty course ? 'selected' : ''}>Selecione um usuário</option>
                        <c:forEach var="u" items="${users}"> <!-- carregando infos users -->
                            <option value="${u.id}"
                                ${not empty course && course.user.id == u.id ? 'selected' : ''}>
                                ${u.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/courses" class="btn btn-default">Cancelar</a>
                    <button type="submit" class="btn btn-primary">
                        ${not empty course ? 'Atualizar' : 'Cadastrar'} Curso
                    </button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
