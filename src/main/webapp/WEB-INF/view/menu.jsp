<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="menu.message"/> </title>
    <%@include file="/WEB-INF/include/head.jspf" %>
</head>
<body>
<%@include file="../include/navbar.jspf"%>
<div style="color: red ; text-align: center;">
    <c:if test="${errorMessage != null}">
        <fmt:message key="${errorMessage}"/>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
</div>

<form action="controller" method="get">
    <input type="hidden" name="command" value="menu">

    <div class="form-row align-items-center">
        <div class="col-auto">
            <label  for="priceFrom"><fmt:message key="priceFrom.message"/></label>
            <input type="number" name = "priceFrom" id="priceFrom" placeholder="0" min = 0 value="<c:out value="${sortMap.priceFrom}" default="" />">
        </div>
        <div class="col-auto">
            <label for="priceTo"><fmt:message key="priceTo.message"/></label>
            <input type="number" id="priceTo" name = "priceTo" placeholder="1000" min = 0 value="<c:out value="${sortMap.priceTo}" default="" />">
        </div>

        <div class="col-auto">
            <div class="form-check mb-2">

                <select name="categoryId" id = "categoryId">
                    <option value=""><fmt:message key="choseCategory.message"/> </option>
                    <c:forEach items="${categories}" var="categoryChoose">
                        <option value= ${categoryChoose.id} ${sortMap.categoryId == categoryChoose.id ? ' selected' : ''}>
                                ${categoryChoose.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="col-auto">
            <div class="form-check mb-2">
                <select name="sortBy">
                    <option value=""><fmt:message key="choseSorting.message"/></option>
                    <option value="ORDER BY dish.name" ${sortMap.sortBy == 'ORDER BY dish.name' ? ' selected' : ''}><fmt:message key="nameAsc.message"/></option>
                    <option value="ORDER BY dish.name Desc" ${sortMap.sortBy == 'ORDER BY dish.name Desc' ? ' selected' : ''}><fmt:message key="nameDesc.message"/></option>
                    <option value="ORDER BY price" ${sortMap.sortBy == 'ORDER BY price' ? ' selected' : ''}><fmt:message key="priceAsc.message"/></option>
                    <option value="ORDER BY price Desc" ${sortMap.sortBy == 'ORDER BY price Desc' ? ' selected' : ''}><fmt:message key="priceDesc.message"/></option>
                </select>
            </div>
        </div>

        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-2"><fmt:message key="apply.message"/></button>
        </div>
    </div>
</form>

    <main>
        <div>
            <table id = "menuTable" class="table table-bordered table-striped">
                <head>
                    <th><fmt:message key="image.message"/></th>
                    <th><fmt:message key="dishName.message"/></th>
                    <th><fmt:message key="ingredients.message"/></th>
                    <th><fmt:message key="weight.message"/></th>
                    <th><fmt:message key="calories.message"/></th>
                    <th><fmt:message key="price.message"/></th>
                    <th><fmt:message key="category.message"/></th>
                    <th><fmt:message key="setQuantity.message"/></th>
                    <th><fmt:message key="addToCart.message"/></th>
                    <c:if test="${user.role == 'MANAGER'}">
                        <th><fmt:message key="edit.message"/></th>
                        <th><fmt:message key="delete.message"/></th>
                    </c:if>
                </head>

                <body>
                <c:forEach items="${dishes}" var="dish">
                    <tr>
                        <td>
                            <img src="${dish.imagePath}" width="100" height="100" alt="${pageContext.request.contextPath}/images/notFound.jpg">
                        </td>
                        <td>
                                ${dish.name}
                        </td>
                        <td>
                                ${dish.ingredients}
                        </td>
                        <td>
                                ${dish.weight}
                        </td>
                        <td>
                                ${dish.calories}
                        </td>
                        <td>
                                ${dish.price}
                        </td>
                        <td>
                                ${dish.category.name}
                        </td>
                        <form action="controller?command=addToCart" method="post">
                            <input type = "hidden" name = "dishId" value="${dish.id}">
                            <td>
                                <input type="number" id="quantity" name="quantity" required
                                       min="1" max="100">
                            </td>
                            <td>
                                <button class="btn-secondary" type="submit"><fmt:message key="add.message"/></button>
                            </td>
                        </form>

                        <c:if test="${user.role == 'MANAGER'}">
                            <form action="controller" method="get">
                                <input type = "hidden" name = "command" value="editDish">
                                <input type = "hidden" name = "dishId" value="${dish.id}">
                                <td>
                                    <button class="btn-secondary" type="submit"><fmt:message key="edit.message"/></button>
                                </td>
                            </form>

                            <form action="controller" method="post">
                                <input type = "hidden" name = "command" value="deleteDish">
                                <input type = "hidden" name = "dishId" value="${dish.id}">
                                <td>
                                    <button  class="btn-secondary" type="submit"><fmt:message key="delete.message"/> </button>
                                </td>
                            </form>
                        </c:if>

                    </tr>
                </c:forEach>
                </body>

            </table>
        </div>

        <div >
            <nav class="float-left">
                <ul class="pagination">
                    <li class="page-item ">
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="menu">
                            <input type="hidden" name="menuPageNumber" value="1">
                            <div class="col-auto">
                                <button type="submit" class="btn btn-primary mb-2" ${(menuPageNumber == 1) ? 'disabled' : ''} ><fmt:message key="backToFirstPage.message"/></button>
                            </div>
                        </form>
                    </li>

                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="menu">
                        <input type="hidden" name="menuPageNumber" value="${menuPageNumber - 1}">
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary mb-2" ${(menuPageNumber > 1) ? '' : 'disabled'} ><fmt:message key="previous.message"/></button>
                        </div>
                    </form>
                    </li>

                    <li class="page-item ">
                        <p class="page-link "><c:out value="${menuPageNumber}"/></p>
                    </li>

                    <li class="page-item ">
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="menu">
                            <input type="hidden" name="menuPageNumber" value="${menuPageNumber + 1}">
                            <div class="col-auto">
                                <button type="submit" class="btn btn-primary mb-2" ${hasNextPage ? '' : 'disabled'} ><fmt:message key="next.message"/></button>
                            </div>
                        </form>
                    </li>
                </ul>
            </nav>
        </div>
    </main>
</body>
</html>
