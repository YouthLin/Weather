<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chenml
  Date: 2016/7/21
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--weather content begin-->
<%String index = pageContext.getServletContext().getContextPath();%>
<div class="weather">

    <h3><a href="<%=index%>/weather.html?id=${city.cityId}">${city.cityNameCN} - 最新七天天气预报</a></h3>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th class="time">时间</th>
            <th class="day">白天</th>
            <th class="night">夜间</th>
            <th class="temp">温度</th>
            <th class="desc">天气</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${records}" var="record">
            <tr>
                <td class="time">${record.time}</td>
                <td class="day"><img src="<%=index%>/static/images/small/day/Day${record.dayIconNum}.png" alt=""></td>
                <td class="night"><img src="<%=index%>/static/images/small/night/Night${record.nightIconNum}.png"
                                       alt=""></td>
                <td class="temp">${record.temperature}</td>
                <td class="desc">${record.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


<!--weather content end-->
