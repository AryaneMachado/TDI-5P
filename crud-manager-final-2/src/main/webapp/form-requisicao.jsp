<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">
			${not empty requisicao ? 'Atualizar' : 'Adicionar'} Requisição
		</h3>

		<form action="${pageContext.request.contextPath}/requisicao/${action}" method="POST">

			<input type="hidden" name="id" value="${requisicao.getId()}" />

			<div class="row">
				<div class="form-group col-md-6">
					<label for="descricao">Descrição</label>
					<input type="text" class="form-control" id="descricao" name="descricao"
					       placeholder="Informe a descrição"
					       required
					       oninvalid="this.setCustomValidity('Por favor, informe a descrição.')"
					       oninput="setCustomValidity('')"
					       value="${requisicao.getDescricao()}" />
				</div>

				<div class="form-group col-md-6">
					<label for="quantidade">Quantidade</label>
					<input type="number" class="form-control" id="quantidade" name="quantidade"
					       placeholder="Informe a quantidade"
					       required
					       oninvalid="this.setCustomValidity('Por favor, informe a quantidade.')"
					       oninput="setCustomValidity('')"
					       value="${requisicao.getQuantidade()}" />
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="dataRequisicao">Data da Requisição</label>
					<input type="date" class="form-control" id="dataRequisicao" name="dataRequisicao"
					       required
					       oninvalid="this.setCustomValidity('Por favor, informe a data da requisição.')"
					       oninput="setCustomValidity('')"
					       value="${requisicao.getDataRequisicao()}" />
				</div>

				<div class="form-group col-md-4">
					<label for="status">Status</label>
					<select class="form-control" id="status" name="status" required
					        oninvalid="this.setCustomValidity('Por favor, selecione o status.')"
					        oninput="setCustomValidity('')">
						<option value="">Selecione o status</option>
						<option value="Pendente" ${requisicao.getStatus() == 'Pendente' ? 'selected' : ''}>Pendente</option>
						<option value="Aprovado" ${requisicao.getStatus() == 'Aprovado' ? 'selected' : ''}>Aprovado</option>
						<option value="Negado" ${requisicao.getStatus() == 'Negado' ? 'selected' : ''}>Negado</option>
					</select>
				</div>

				<div class="form-group col-md-4">
					<label for="usuario">Usuário</label>
					<select id="usuario" class="form-control selectpicker" name="usuarioId"
					        required
					        oninvalid="this.setCustomValidity('Por favor, selecione um usuário.')"
					        oninput="setCustomValidity('')">
						<option value="" ${empty requisicao ? 'selected' : ''}>Selecione um usuário</option>
						<c:forEach var="user" items="${usuarios}">
							<option value="${user.getId()}"
								${not empty requisicao && requisicao.getUsuario().getId() == user.getId() ? 'selected' : ''}>
								${user.getName()}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/requisicao" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">
						${not empty requisicao ? 'Atualizar' : 'Cadastrar'} Requisição
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
