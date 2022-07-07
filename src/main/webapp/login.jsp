<%@include file="WEB-INF/include/header.jspf"%>

<head>
	<%@include file="/WEB-INF/include/head.jspf" %>
	<title><fmt:message key="login.message"/> </title>
</head>

<body>
<%@include file="WEB-INF/include/navbar.jspf"%>
<div class="outer">
	<h2><fmt:message key="login.message"/> </h2>

	<form action="controller" method="post">
		<input type="hidden" name="command" value="login"><br>
		<div class="container">
			<div class="form-group">
				<label for="email"><b><fmt:message key="email.message"/></b></label>
				<input type="email" class="form-control" placeholder="<fmt:message key="enterEmail.message"/>" id="email" name="email" required value="<c:out value="${email}" default="" />">
			</div>

			<div class="form-group" >
				<label for="password"><b><fmt:message key="password.message"/></b></label>
				<input type="password" class="form-control" placeholder="<fmt:message key="enterPassword.message"/>" id="password" name="password"  maxlength="64" required>
			</div>

			<div style="color: red ; text-align: center;">
				<c:if test="${errorMessage != null}">
					<fmt:message key="${errorMessage}"/>
					<c:remove var="errorMessage" scope="session"/>
				</c:if>
			</div>
			<div class="col-auto">
				<button class="btn btn-primary mb-2" type="submit"><fmt:message key="login.message"/>!</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>
