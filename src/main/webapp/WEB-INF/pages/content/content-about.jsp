<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--about content begin-->
<div class="main well">
    <%String index = pageContext.getServletContext().getContextPath();%>
    <h3><a href="<%=index%>" style="display: inline-block;text-decoration: none;">
        <img src="<%=index%>/static/images/logo.jpg" alt="logo">灵天气™</a>
    </h3>
    <p>数据来源：中国天气通（<a href="http://www.weather.com.cn/">http://www.weather.com.cn/</a>）</p>
    <p>技术支持：<a href="http://youthlin.com/">Youth．霖</a></p>
    <p>代码开源：<a href="https://github.com/YouthLin/Weather">https://github.com/YouthLin/Weather</a></p>
</div>
<!--about content end-->
