<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
<title>Facebook</title>
</head>
<body>
	<div class="container">
		<div class="row pt-5">
			<div class="col-md-1"></div>
			<div class="col-md-10">

				<a href="index.jsp" class="btn btn-secondary"> <i
					class="bi bi-house"></i>
				</a>

				<div class="d-flex justify-content-between align-items-center mb-3">

					<h1 class="m-0">Posts</h1>

					<a href="form_post.jsp" class="btn btn-primary"> Novo Post </a>

				</div>

				<table class="table table-hover">
					<thead>
						<tr>
							<th scope="col">Id</th>
							<th scope="col">Conteudo</th>
							<th scope="col">Data da Postagem</th>
							<th scope="col">ID Usuario</th>
							<th scope="col">Editar</th>
							<th scope="col">Remover</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="post" items="${posts}">
							<tr>
								<th scope="row">${post.getId()}</th>
								<td scope="row">${post.getContent()}</td>
								<td scope="row">${post.getPostDate()}</td>
								<td scope="row">${post.getUser().getId()}-
									${post.getUser().getName()}</td>
								<td>
									<form action="${pageContext.request.contextPath}/posts/edit" method="POST" >
										<input id="post_update_id" name="post_update_id" value="${post.getId()}" hidden="">
										<button class="bi bi-pencil-square btn btn-sm btn-warning mb-1" type="submit"> Editar</button>
									</form>
								</td>
								<td>
									<form action="${pageContext.request.contextPath}/posts/delete" method="POST">
										<input id="post_delete_id" name="post_delete_id" value="${post.getId()}" hidden="">
										<button class="bi bi-trash btn btn-sm btn-danger mb-1" type="submit"></button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-md-1"></div>
		</div>
		<div class="row"></div>
	</div>
	<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>