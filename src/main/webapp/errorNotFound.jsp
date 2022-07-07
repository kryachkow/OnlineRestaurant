<%@include file="WEB-INF/include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="errorNotFound.message"/> </title>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="WEB-INF/include/navbar.jspf"%>
<div class="col-auto">
    <c:choose>
        <c:when test="${banMessage != null}">
            <fmt:message key="${banMessage}"/>
        </c:when>
        <c:otherwise>
            <br> <a href="index.jsp"><fmt:message key="goToMainPage.message"/> </a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
