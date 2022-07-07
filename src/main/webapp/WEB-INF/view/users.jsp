<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="users.message"/></title>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="../include/navbar.jspf"%>

<form action="controller" method="get" id="searchForm">
    <input type="hidden" name="command" value="users">
    <div class="form-row align-items-center">
        <div class="col-auto">
            <label for="userNameSearch"><fmt:message key="nameSearchMessage.message"/></label>
            <input type="text" name = "userNameSearch" id="userNameSearch" placeholder="<fmt:message key="name.message"/>" value="<c:out value="${sortMap.userNameSearch}" default=""/>">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-2"><fmt:message key="apply.message"/></button>
        </div>
    </div>
</form>

    <table class="table table-bordered table-striped">
        <title>
            <fmt:message key="users.message"/>
        </title>

        <tr>
            <th><fmt:message key="userName.message"/></th>
            <th><fmt:message key="userEmail.message"/></th>
            <th><fmt:message key="updateBanStatus.message"/></th>
        </tr>
        <body>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:choose>
                        <c:when test="${user.banned}">
                            <form  action="controller?command=users&id=${user.id}&toDo=unban" method="post"  >
                                <button class="btn-secondary" type="submit"><fmt:message key="unBan.message"/></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form  action="controller?command=users&id=${user.id}&toDo=ban" method="post" >
                                <button class="btn-secondary" type="submit"><fmt:message key="ban.message"/></button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
          </c:forEach>
        </body>
    </table>

<div class="col">
    <nav class="float-end">
        <ul class="pagination">
            <li class="page-item ">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="users">
                    <input type="hidden" name="usersPageNumber" value="1">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-2" ${(usersPageNumber == 1) ? 'disabled' : ''} ><fmt:message key="backToFirstPage.message"/> </button>
                    </div>
                </form>
            </li>

            <form action="controller" method="get">
                <input type="hidden" name="command" value="users">
                <input type="hidden" name="usersPageNumber" value="${usersPageNumber - 1}">
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-2" ${(usersPageNumber > 1) ? '' : 'disabled'} ><fmt:message key="previous.message"/></button>
                </div>
            </form>
            </li>

            <li class="page-item ">
                <p class="page-link "><c:out value="${usersPageNumber}"/></p>
            </li>

            <li class="page-item ">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="users">
                    <input type="hidden" name="usersPageNumber" value="${usersPageNumber + 1}">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-2" ${hasNextPage ? '' : 'disabled'} ><fmt:message key="next.message"/></button>
                    </div>
                </form>
            </li>
        </ul>
    </nav>
</div>



</body>
</html>
