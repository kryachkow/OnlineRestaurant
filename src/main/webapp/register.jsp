<%@include file="WEB-INF/include/header.jspf"%>

<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <title><fmt:message key="register.message"/></title>
    <script type="application/javascript" src = "js/validation.js"></script>
</head>

<body>
<%@include file="WEB-INF/include/navbar.jspf"%>
<div class="outer">
    <h2><fmt:message key="register.message"/> </h2>

    <form name = "validateForm" action="controller" method="post" onsubmit = "return matchPasswords()" >
        <input type="hidden" value="${currentLocale}" id="currentLocale" name="currentLocale">
        <input type="hidden" name="command" value="register"><br>

        <div class="container">
            <div class="form-group">
                <label for="email"><fmt:message key="email.message"/></label>
                <input class="form-control" type="email" placeholder="<fmt:message key="enterEmail.message"/>" name="email" id="email" required value="<c:out value="${sessionScope.registerMap.email}" default=""/> ">
            </div>

            <div class="form-group">
                <label for="name"><fmt:message key="name.message"/></label>
                <input  class="form-control" type="text" placeholder="<fmt:message key="enterName.message"/>" name="name" id="name" required value="<c:out value="${sessionScope.registerMap.name}" default=""/>" >
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="newPassword"><fmt:message key="password.message"/> </label>
                    <input  class="form-control" type="password" placeholder="<fmt:message key="enterPassword.message"/> " name="newPassword" id="newPassword" required value="<c:out value="${sessionScope.registerMap.newPassword}" default=""/>" minlength="8" maxlength="24">
                </div>

                <div class="form-group col-md-6">
                    <label for="repeatNewPassword"><fmt:message key="repeatPassword.message"/> </label>
                    <input class="form-control" type="password" placeholder="<fmt:message key="repeatPassword.message"/> " name="repeatNewPassword" id="repeatNewPassword" required  minlength="8" maxlength="24">
                </div>
            </div>
            <div>
                <span id="passwordLock" style="color:red"></span>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="phone"><fmt:message key="phone.message"/> </label>
                    <input class="form-control" type="tel" placeholder="095-000-0000" name="phone" id="phone" required pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" value="<c:out value="${sessionScope.registerMap.phone}" default=""/>" >
                </div>
                <div class="form-group col-md-6">
                    <label for="address"><fmt:message key="address.message"/></label>
                    <input class="form-control" type="text" placeholder="<fmt:message key="enterAddress.message"/>" name="address" id="address" required value="<c:out value="${sessionScope.registerMap.address}" default=""/>">
                </div>
            </div>

            <div style="color: red ; text-align: center;">
                <c:if test="${sessionScope.errorMessage != null}">
                    <fmt:message key="${sessionScope.errorMessage}"/>
                    <c:remove var="errorMessage" scope="session"/>
                </c:if>
            </div>
            <c:remove var="registerMap" scope="session"/>

            <div class="col-auto">
                <button class="btn btn-primary mb-2" type="submit"><fmt:message key="register.message"/> !</button>
            </div>
        </div>


    </form>
</div>
</body>
</html>