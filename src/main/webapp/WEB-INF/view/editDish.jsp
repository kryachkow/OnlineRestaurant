<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="editDish.message"/></title>
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
    <form action="controller?command=editDish" method="post">
        <input type = "hidden" name = "dishId" value="${dish.id}">
        <input type = "hidden" name="toDo" value="edit">

        <div class="form-group">
            <label for="dishName"><fmt:message key="dishName.message"/></label>
            <input type="text" minlength = "4" maxlength="30" name="dishName" id="dishName" class="form-control" value="<c:out value="${dish.name}" default=""/>" required >
        </div>

        <div class="form-group">
            <label for="dishIngredients"><fmt:message key="ingredients.message"/></label>
            <input type="text" minlength = "4" maxlength="200" name="dishIngredients" id="dishIngredients"  class="form-control" value="<c:out value="${dish.ingredients}" default=""/>" required>
        </div>

        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="dishWeight"><fmt:message key="weight.message"/></label>
                <input type="number" min = "20" name="dishWeight" id="dishWeight"  class="form-control" value="<c:out value="${dish.weight}" default=""/>" required pattern="[0-9]+">
            </div>

            <div class="form-group col-md-4">
                <label for="dishCalories"><fmt:message key="calories.message"/></label>
                <input type="number" min = "20" name="dishCalories" id="dishCalories"  class="form-control" value="<c:out value="${dish.calories}" default=""/>" required pattern="[0-9]+">
            </div>

            <div class="form-group col-md-4">
                <label for="dishPrice"><fmt:message key="price.message"/></label>
                <input type="number" min = "20" name="dishPrice" id="dishPrice"  class="form-control" value="<c:out value="${dish.price}" default=""/>" required pattern="[0-9]+">
            </div>
        </div>



        <div class="col-auto">
            <label for="categoryId"><fmt:message key="category.message"/></label>
            <div class="form-check mb-2">
                <select class="custom-select mr-sm-2" name="categoryId" id = "categoryId">
                    <c:forEach items="${categories}" var="categoryChoose">
                        ${dish.category.id == categoryChoose.id ? ' selected' : ''}
                        <option value= ${categoryChoose.id} ${dish.category.id == categoryChoose.id ? ' selected' : ''}>
                                ${categoryChoose.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="dishImagePath"><fmt:message key="imagePath.message"/></label>
            <input type="text" maxlength = "400" name="dishImagePath" id="dishImagePath"  class="form-control" value="<c:out value="${dish.imagePath}" default=""/>" required>
        </div>

        <button class="btn-primary" type="submit"><fmt:message key="editDish.message"/> </button>
    </form>

</body>
</html>
