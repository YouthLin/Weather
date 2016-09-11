<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<p>灵天气 天有不测风云，不灵别怪我哦</p>
<p>
    &copy; <%=new SimpleDateFormat("YYYY").format(new Date())%>
    <a href="${pageContext.request.servletContext.contextPath}/index.html">灵天气</a> |
    Powered By <a href="http://youthlin.com/">Youth．霖</a>
</p>
