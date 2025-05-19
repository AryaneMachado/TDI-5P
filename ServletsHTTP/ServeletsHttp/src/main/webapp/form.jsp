<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Teste</title>
</head>
<body>
    <h1>Formulário envio de Dados (POST / request-body)</h1>
    <form action="request-body" method="post">
        Nome: <input type="text" name="nome"><br>
        Email: <input type="email" name="email"><br>
        Mensagem: <textarea name="mensagem"></textarea><br>
        <button type="submit">Enviar</button>
    </form>
    <p><a href="index.jsp">← Voltar</a></p>
</body>
</html>
