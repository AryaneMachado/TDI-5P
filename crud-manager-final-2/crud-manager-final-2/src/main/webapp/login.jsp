<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp" %>
    <title>Login</title>
</head>
<body>
    <div class="container" style="margin-top: 100px; max-width: 400px;">
        <h3 class="text-center">Login</h3>

        <form action="${pageContext.request.contextPath}/logincontroller" method="post">
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" class="form-control" id="email" name="email"
                       placeholder="Digite seu e-mail"
                       required
                       oninvalid="this.setCustomValidity('Por favor, informe o e-mail.')"
                       oninput="setCustomValidity('')"
                       autofocus />
            </div>

            <div class="form-group">
                <label for="password">Senha</label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="Digite sua senha"
                       required
                       oninvalid="this.setCustomValidity('Por favor, informe a senha.')"
                       oninput="setCustomValidity('')" />
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <div class="form-group text-center">
                <button type="submit" class="btn btn-primary">Entrar</button>
            </div>
        </form>
    </div>
</body>
</html>
