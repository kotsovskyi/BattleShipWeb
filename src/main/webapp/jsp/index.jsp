<%@ page import="com.kotsovskyi.server.Server" %>
<%-- Created by IntelliJ IDEA. --%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Be my guest</title>
</head>
<body>

<a>Players online: <%= Server.numberOfOnline %></a>

<div id="login">
    <form class="form-signin" method="POST" action="Controller">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" name="login" class="input-block-level" placeholder="Login">
        <input type="password" name="password" class="input-block-level" placeholder="Password">
        <button class="btn btn-large btn-primary" name="action" value ="login" type="submit">Sign in</button>
    </form>
</div>
</body>
</html>