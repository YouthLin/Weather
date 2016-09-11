<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>
<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <tiles:insertAttribute name="head"/>
    <title><tiles:getAsString name="title" ignore="true">天气</tiles:getAsString></title>
</head>
<body>
<div class="wrap" id="wrap">
    <tiles:insertAttribute name="header"/>
    <div class="container"><tiles:insertAttribute name="content"/></div>
    <div class="clear" id="end"></div>
</div><!--.wrap-->
<footer id="copyright">
    <tiles:insertAttribute name="footer"/>
</footer>
</body>
</html>
