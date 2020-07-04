<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name ="content">
        <h2>タスク一覧</h2>
        <ul>
            <c:forEach var="todo" items="${tasks}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?id=${todo.id}">
                        <c:out value="${todo.id}" />
                    </a>
                    :<c:out value="${todo.content}" />
                </li>
            </c:forEach>
        </ul>

        <p><a href="${pageContext.request.contextPath}/new">新規タスク入力</a></p>
     </c:param>
</c:import>
