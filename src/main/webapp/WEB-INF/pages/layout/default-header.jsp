<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    String index = pageContext.getRequest().getServletContext().getContextPath();
%>
<header class="header-bar">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%=index%>">
                    <img alt="Logo" src="<%=index%>/static/images/logo.jpg" width="50" height="50">
                </a>
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <form:form commandName="city" action="search.html" method="get" cssClass="navbar-form navbar-right">
                <input name="cityName" class="form-control" autofocus="autofocus" id="cityName"
                       placeholder="切换城市：输入城市名称(中文/拼音)..."/>
            </form:form>
            <!-- 搜索提示框 -->
            <div class="suggest" id="search_suggest">
                <ul id="search_result" class="search-result-header"></ul>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="header-li header-li-home"><a href="<%=index%>/index.html">首页</a></li>
                    <li class="header-li header-li-weather"><a href="<%=index%>/weather.html">天气</a></li>
                    <li class="header-li header-li-about"><a href="<%=index%>/about.html">关于</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>
</header>
