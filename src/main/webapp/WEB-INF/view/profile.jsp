<%@include file="../include/header.jspf"%>

<html>
<head>
    <title><fmt:message key="myProfile.message"/></title>
    <script type="application/javascript" src = "${pageContext.request.contextPath}/js/validation.js"></script>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="../include/navbar.jspf"%>


<div style="color: green ; text-align: center;">
    <c:if test="${message != null}">
        <fmt:message key="${message}"/>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
</div>

<div style="color: red ; text-align: center;">
    <c:if test="${errorMessage != null}">
        <fmt:message key="${errorMessage}"/>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
</div>

    <div class="col-auto">
        <fmt:message key="myProfile.message"/>:
        <p><fmt:message key="name.message"/> : ${user.name}</p>
        <p><fmt:message key="email.message"/>: ${user.email}</p>
        <p><fmt:message key="phone.message"/> : ${user.phone}</p>
        <p><fmt:message key="address.message"/>: ${user.address}</p>
    </div>



<div class="container">
    <div class="row align-items-center">
        <div class="col">
            <form  action="controller" method="post" >
                <input type="hidden" name="command" value="editProfile">
                <input type="hidden" name="userId" value="${user.id}">
                <div class="container">
                    <div class="form-group">
                        <label for="name"><fmt:message key="name.message"/> </label>
                        <input type="text" placeholder="<fmt:message key="enterName.message"/> " name="name" id="name" required value="<c:out value="${user.name}" default=""/>" >
                    </div>

                    <div class="form-group ">
                        <label for="email"><fmt:message key="email.message"/></label>
                        <input type="email" placeholder="<fmt:message key="enterEmail.message"/>" name="email" id="email" required value="<c:out value="${user.email}" default=""/> ">
                    </div>

                    <div class="form-group">
                        <label for="phone"><fmt:message key="phone.message"/></label>
                        <input type="tel" placeholder="095-000-0000" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="phone" id="phone" required value="<c:out value="${user.phone}" default=""/>" >
                    </div>

                    <div class="form-group">
                        <label for="address"><fmt:message key="address.message"/></label>
                        <input type="text" placeholder="<fmt:message key="enterAddress.message"/>" name="address" id="address" required value="<c:out value="${user.address}" default=""/>">
                    </div>


                    <div class="form-group">
                        <label for="password"><fmt:message key="passwordCheck.message"/></label>
                        <input type="password" placeholder="<fmt:message key="passwordCheck.message"/>" name="password" id="passwordCheck" required minlength="5" maxlength="24">
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="editProfileData"/></button>
                </div>
            </form>
        </div>
        <div class="col">
            <form  name = "validateForm" onsubmit = "return matchPasswords()" action="controller" method="post" >
                <input type="hidden" value="${currentLocale}" id="currentLocale" name="currentLocale">
                <input type="hidden" name="command" value="editProfile">
                <input type="hidden" name="userId" value="${user.id}">
                <div class="container">
                    <div class="form-group ">
                        <label for="password"><fmt:message key="oldPassword.message"/></label>
                        <input type="password" placeholder="<fmt:message key="oldPassword.message"/>" name="password" id="password" required minlength="5" maxlength="24">
                    </div>
                    <div class="form-group ">
                        <label for="newPassword"><fmt:message key="newPassword.message"/></label>
                        <input type="password" placeholder="<fmt:message key="newPassword.message"/>" name="newPassword" id="newPassword" required minlength="8" maxlength="24">
                    </div>
                    <div class="form-group">
                        <label for="repeatNewPassword"><fmt:message key="repeatNewPassword.message"/></label>
                        <input type="password" placeholder="<fmt:message key="repeatNewPassword.message"/>" name="repeatNewPassword" id="repeatNewPassword" required minlength="8" maxlength="24">
                    </div>
                    <div>
                        <span id="passwordLock" style="color:red"></span>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary"><fmt:message key="changePassword.message"/></button>
                    </div>
                </div>

            </form>

        </div>
    </div>
</div>
</body>
</html>
