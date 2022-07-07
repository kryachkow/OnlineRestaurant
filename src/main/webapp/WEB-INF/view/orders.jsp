<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="orders.message"/></title>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script type="application/javascript" src="js/search.js"></script>
</head>
<body>
<%@include file="../include/navbar.jspf"%>
<c:choose>

    <c:when test="${user.role =='MANAGER'}">
        <form action="controller" method="get" id="searchForm">
            <input type="hidden" name="command" value="orders">
            <div class="form-row align-items-center" >
                <div class="col-auto">
                    <label for="nameSearch"><fmt:message key="nameSearchMessage.message"/></label>
                    <input type="text" name = "nameSearch" id="nameSearch" placeholder="<fmt:message key="name.message"/>" value="<c:out value="${sortMap.nameSearch}" default=""/>">
                </div>
                <div class="col-auto">
                    <div class="form-check">
                        <label for="statusChose"><fmt:message key="statusChose.message"/></label>
                        <select name="statusChose" id="statusChose">
                            <option value=""><fmt:message key="statusChose.message"/></option>
                            <option value="1" ${sortMap.statusChose == 1 ? ' selected' : ''}><fmt:message key="created.message"/> </option>
                            <option value="2" ${sortMap.statusChose == 2 ? ' selected' : ''}><fmt:message key="cooking.message"/></option>
                            <option value="3" ${sortMap.statusChose == 3 ? ' selected' : ''}><fmt:message key="delivering.message"/></option>
                            <option value="4" ${sortMap.statusChose == 4 ? ' selected' : ''}><fmt:message key="done.message"/></option>
                            <option value="5" ${sortMap.statusChose == 5 ? ' selected' : ''}><fmt:message key="cancelled.message"/></option>
                        </select>
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-2"><fmt:message key="apply.message"/></button>
                </div>
            </div>

        </form>
    </c:when>

    <c:otherwise>
        <fmt:message key="myOrders.message"/>
    </c:otherwise>
</c:choose>


<table class="table table-bordered" id="orderTable" data-filter-control="true" data-show-search-clear-button="true">
    <tr>
        <th><fmt:message key="userName.message"/></th>
        <th><fmt:message key="userPhone.message"/></th>
        <th><fmt:message key="deliveryAddress.message"/></th>
        <th><fmt:message key="userEmail.message"/></th>
        <th><fmt:message key="orderStatus.message"/></th>
        <th><fmt:message key="createTime.message"/></th>
        <th><fmt:message key="orderContent.message"/></th>
        <th><fmt:message key="totalPrice.message"/></th>
    </tr>


    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.user.name}</td>
            <td>${order.user.phone}</td>
            <td>${order.deliveryAddress}</td>
            <td>${order.user.email}</td>
            <td>
                <c:choose>
                    <c:when test="${user.role =='MANAGER'}">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="updateOrderStatus">
                            <input type="hidden" name="orderId" value="${order.id}">

                            <div class="col-auto">
                                <div class="form-check mb-2">
                                    <select name="changeStatusId">
                                        <option value="1" ${order.status == "CREATED" ? ' selected' : ''}><fmt:message key="created.message"/></option>
                                        <option value="2" ${order.status == "COOKING" ? ' selected' : ''}><fmt:message key="cooking.message"/></option>
                                        <option value="3" ${order.status == "DELIVERING" ? ' selected' : ''}><fmt:message key="delivering.message"/></option>
                                        <option value="4" ${order.status == "DONE" ? ' selected' : ''}><fmt:message key="done.message"/></option>
                                        <option value="5" ${order.status == "CANCELLED" ? ' selected' : ''}><fmt:message key="cancelled.message"/></option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-auto">
                                <button type="submit" class="btn btn-primary mb-2"><fmt:message key="updateStatus.message"/></button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        ${order.status}
                    </c:otherwise>
                </c:choose>

            </td>
            <td>${order.createTime}</td>
            <td>
                <c:forEach items="${order.contentList}" var="content">
                    ${content.dish.name} - ${content.quantity} <br>
                </c:forEach>
            </td>
            <td>${order.totalPrice}</td>
        </tr>
    </c:forEach>

</table>

<div class="col">
    <nav class="float-end">
        <ul class="pagination">
            <li class="page-item ">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="orders">
                    <input type="hidden" name="orderPageNumber" value="1">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-2" ${(orderPageNumber == 1) ? 'disabled' : ''} ><fmt:message key="backToFirstPage.message"/></button>
                    </div>
                </form>
            </li>

                <form action="controller" method="get">
                    <input type="hidden" name="command" value="orders">
                    <input type="hidden" name="orderPageNumber" value="${orderPageNumber -1}">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-2" ${(orderPageNumber > 1) ? '' : 'disabled'} ><fmt:message key="previous.message"/></button>
                    </div>
                </form>
            </li>

            <li class="page-item ">
                <p class="page-link "><c:out value="${orderPageNumber}"/></p>
            </li>

            <li class="page-item ">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="orders">
                    <input type="hidden" name="orderPageNumber" value="${orderPageNumber + 1}">
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
