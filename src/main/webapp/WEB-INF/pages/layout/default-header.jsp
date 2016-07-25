<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>
<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<tilesx:useAttribute name="current-menu" id="menu"/>
<%
    String index = pageContext.getRequest().getServletContext().getContextPath();
    String item = "Home";
    if (menu != null) {
        item = (String) menu;
    }
    String curHome = "", curWeather = "", curAbout = "";
    switch (item) {
        case "Home":
            curHome = " class='active'";
            break;
        case "Weather":
            curWeather = " class='active'";
            break;
        case "About":
            curAbout = " class='active'";
            break;
    }
//    System.out.println("menu=" + menu);
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
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li<%=curHome%>><a href="<%=index%>/index.html">首页</a></li>
                    <li<%=curWeather%>><a href="<%=index%>/weather.html">天气</a></li>
                    <li<%=curAbout%>><a href="<%=index%>/about.html">关于</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>
</header>
