<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--begin index content-->
<div class="main">
    <div class="index-item logo">
        <%String index = pageContext.getServletContext().getContextPath();%>
        <h2><a class="index-logo" href="<%=index%>">
            <img src="<%=index%>/static/images/logo.jpg" alt="logo">灵天气™</a>
        </h2>
    </div>
    <div class="index-item search">
        <form:form commandName="city" action="search.html" method="get">
            <form:input path="cityName" placeholder="输入城市名称(中文/拼音)..."
                        cssClass="form-control" autofocus="autofocus"/>
            <input type="hidden" id="city-id"/>
        </form:form>
        <!-- 搜索提示框 -->
        <div class="suggest" id="search_suggest">
            <ul id="search_result"></ul>
        </div>
    </div>
    <div class="index-item hot">
        <dl>
            <dt>热门城市</dt>
            <dd>
                <a class="fix" href="weather.html?id=101010100">北京</a>
                <a class="fix" href="weather.html?id=101020100">上海</a>
                <a class="fix" href="weather.html?id=101030100">天津</a>
                <a class="fix" href="weather.html?id=101040100">重庆</a>
                <c:forEach items="${hotCities}" var="city">
                    <a href="weather.html?id=${city.cityId}">${city.cityNameCN}</a>
                </c:forEach>
            </dd>
        </dl>
    </div>
</div>
<!--end index content-->