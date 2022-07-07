<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="myOrder.message"/></title>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="../include/navbar.jspf"%>

<main>
    <div style="color: red ; text-align: center;">
        <c:if test="${errorMessage != null}">
            <fmt:message key="${errorMessage}"/>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
    </div>
    <div>
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th>
                    <fmt:message key="dishName.message"/>
                </th>
                <th>
                    <fmt:message key="quantity.message"/>
                </th>
                <th>
                    <fmt:message key="price.message"/>
                </th>
                <th>
                    <fmt:message key="remove.message"/>
                </th>
            </tr>
            <c:forEach items="${cart.values()}" var="cartContent">
                <tr>
                    <th>
                        ${cartContent.dish.name}
                    </th>
                    <th>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-1">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="manageCart">
                                        <input type="hidden" name="changeQuantity" value="remove">
                                        <input type = "hidden" name = "changeQuantityDishName" value="${cartContent.dish.name}">
                                        <button type="submit">-</button>
                                    </form>
                                </div>
                                <div class="col-md-1">
                                        ${cartContent.quantity}
                                </div>
                                <div class="col-md-1">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="manageCart">
                                        <input type="hidden" name="changeQuantity" value="add">
                                        <input type = "hidden" name = "changeQuantityDishName" value="${cartContent.dish.name}">
                                        <button type="submit">+</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </th>
                    <th>
                        ${cartContent.price}
                    </th>
                    <th>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="manageCart">
                            <input type="hidden" name="delete" value="delete">
                            <input type="hidden" name="toDeleteDishName" value="${cartContent.dish.name}">
                            <button type="submit"><fmt:message key="remove.message"/></button>
                        </form>
                    </th>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
<fmt:message key="totalPrice.message"/> ${totalPrice}
<hr>
<form action="controller" method="post">
    <input type="hidden" name="command" value="makeOrder">
    <input type="hidden" name="totalPrice" value="${totalPrice}">
    <div class="align-self-auto">
        <label for="deliveryAddress"><fmt:message key="deliveryAddress.message"/></label>
        <input type="text" name="deliveryAddress" id="deliveryAddress" required value=" <c:out value="${user.address}" default=""/>">
    </div>
    <button class="btn-primary" type="submit"><fmt:message key="makeAnOrder.message"/> </button>
</form>
</body>
</html>