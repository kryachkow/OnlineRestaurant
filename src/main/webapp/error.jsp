<%@include file="WEB-INF/include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="error.message"/> </title>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="WEB-INF/include/navbar.jspf"%>
<div class="col-auto">
    <fmt:message key="unexpectedError.message"/>
    <div style="color: red">
        <m:message></m:message>
    </div>
</div>
</body>
</html>
