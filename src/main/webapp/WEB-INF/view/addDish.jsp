<%@include file="../include/header.jspf"%>
<html>
<head>
    <title><fmt:message key="addDish.message"/> </title>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script type="text/javascript" src="../../js/validation.js"></script>
</head>
<body>
<%@include file="../include/navbar.jspf"%>
<div style="color: red ; text-align: center;">
    <c:if test="${errorMessage != null}">
        <fmt:message key="${errorMessage}"/>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
</div>
<div style="color: green ; text-align: center;">
    <c:if test="${message != null}">
        <fmt:message key="${message}"/>
        <c:remove var="message" scope="session"/>
    </c:if>
</div>
<form name= "dishForm" id = "dishForm" action="controller?command=addDish" method="post">
    <input type = "hidden" name="toDo" value="add">
    <div class="form-group">
        <label for="dishName"><fmt:message key="dishName.message"/></label>
        <input type="text" minlength="4"  maxlength="30" name="dishName" id="dishName" class="form-control" placeholder="<fmt:message key="dishName.message"/>"
        value = "<c:out value="${dishMap.dishName}" default=""/>" required >
    </div>

    <div class="form-group">
        <label for="dishIngredients"><fmt:message key="ingredients.message"/></label>
        <input type="text" minlength = "4" maxlength="200" name="dishIngredients" id="dishIngredients"  class="form-control" placeholder="<fmt:message key="ingredients.message"/>" required
               value = "<c:out value="${dishMap.dishIngredients}" default=""/>">
    </div>
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="dishWeight"><fmt:message key="weight.message"/></label>
            <input type="number" min = "20" name="dishWeight" id="dishWeight"  class="form-control" placeholder="<fmt:message key="weight.message"/>" required
                   value = "<c:out value="${dishMap.dishWeight}" default=""/>"
                   pattern="[0-9]+">
        </div>

        <div class="form-group col-md-4">
            <label for="dishCalories"><fmt:message key="calories.message"/></label>
            <input type="number" min = "20" name="dishCalories" id="dishCalories"  class="form-control" placeholder="<fmt:message key="calories.message"/>" required
                   value = "<c:out value="${dishMap.dishCalories}" default=""/>"
                   pattern="[0-9]+">
        </div>

        <div class="form-group col-md-4">
            <label for="dishPrice"><fmt:message key="price.message"/></label>
            <input type="number" min = "20" name="dishPrice" id="dishPrice"  class="form-control" placeholder="<fmt:message key="price.message"/>" required
                   value = "<c:out value="${dishMap.dishPrice}" default=""/>"
                   pattern="[0-9]+">
        </div>
    </div>


    <div>
        <label for="categoryId"><fmt:message key="category.message"/> </label>
            <select class="custom-select mr-sm-2" name="categoryId" id = "categoryId">
                <c:forEach items="${categories}" var="categoryChoose">
                    <option value= ${categoryChoose.id} ${dishMap.categoryId == categoryChoose.id ? ' selected' : ''}>
                            ${categoryChoose.name}
                    </option>
                </c:forEach>
            </select>
    </div>

    <div class="form-group">
        <label for="dishImagePath"><fmt:message key="imagePath.message"/></label>
        <input type="text" maxlength = "400" name="dishImagePath" id="dishImagePath"  class="form-control" placeholder="<fmt:message key="imagePath.message"/>" required
               value = "<c:out value="${dishMap.dishImagePath}" default=""/>">
    </div>

    <button class="btn-primary" type="submit" ><fmt:message key="addDish.message"/></button>
</form>
<c:remove var="dishMap" scope="session"/>
<br>
<br>
<hr>
<br>
<br>
<div class="row align-content-center">
    <div class="col">
        <form action="controller" method="post">
            <div class="form-group">
                <input type="hidden" name="command" value="addCategory">
                <label for="categoryName"><fmt:message key="addCategory.message"/></label>
                <input type="text" id = "categoryName" name = "categoryName" minlength="4" maxlength="45" required>
                <button class="btn-primary" type="submit"><fmt:message key="addCategory.message"/></button>
            </div>
        </form>
    </div>
    <div class="col">
        <div class="table">
                <c:forEach items="${categories}" var="category">
                    <div class="row">
                        <div class="col">
                            ${category.name}
                        </div>
                        <div class="col">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="deleteCategory">
                                <input type="hidden" name="categoryId" value="${category.id}">
                                <button class="btn-secondary" type="submit"><fmt:message key="delete.message"/></button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
        </div>
    </div>
</div>
</body>
</html>