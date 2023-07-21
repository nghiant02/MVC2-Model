<%-- 
    Document   : createNewAccount
    Created on : Mar 15, 2023, 12:44:15 PM
    Author     : nghia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERROS}"/>
            <input type="text" name="txtUsername" value="${param.txtUsername}"
                   size="20" /><br/>
            <c:if test="${not empty errors.usernameLengthError}">
                <font color ="red">
                ${errors.usernameLengthError}
                <font><br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color ="red">
                ${errors.usernameIsExisted}
                <font><br/>
            </c:if>
            <input type="text" name="txtPassword" value="${param.txtFullname}"
                   size="30" /><br/>
            <c:if test="${not empty errors.passwordLengthError}">
                <font color ="red">
                ${errors.passwordLengthError}
                <font><br/>
            </c:if>
            <input type="text" name="txtConfirm" value="" size="30" /><br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color ="red">
                ${errors.confirmNotMatched}
                <font><br/>
            </c:if>
            <input type="text" name="txtFullname" value="" size="50" /><br/>
            <c:if test="${not empty errors.fullnameLengthError}">
                <font color ="red">
                ${errors.fullnameLengthError}
                <font><br/>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>     
    </body>
</html>
