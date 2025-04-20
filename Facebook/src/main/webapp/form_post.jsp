<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.time.LocalDate, java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro Post</title>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
<title>Facebook</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-2"></div>

			<form action="${pageContext.request.contextPath}/posts/save"
				method="POST" class="col-8">
				<h1>Criar Post</h1>

				<input type="hidden" id="post_id" name="post_id"
					value="${post.getId()}" required>

				<div class="mb-3">
					<label for="content_post" class="form-label">Conteudo</label> <input
						type="text" id="content_post" name="content" class="form-control" value="${post.getContent()}" required>
				</div>

				<div class="mb-3">
					<label for="user_id" class="form-label">ID Usuário da
						postagem</label> 
						<input class="form-control" type="text" id="user_post_id" name="user_post_id" 
						 value="${post.getUser().getId()}" required>
				</div>

				<button type="submit" class="btn btn-primary">Enviar</button>
			</form>

			<div class="col-2"></div>
		</div>
	</div>

	<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>