<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="footer">
	© 2012 Surprizer, Inc. <c:if test="${!empty(user)}"> Logged in as ${user.name}.</c:if>
</div>