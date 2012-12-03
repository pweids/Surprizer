<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" />
<body>
<div id="stripe"></div>
	<div id="header">
       <h1 class="logo">
        SURPRIZER
       </h1>
	</div>
    <div id="main">
        <div id="content-box">
            <span class="content-text">Log in to make, edit, and share gift lists</span>
            <jsp:include page="error-list.jsp" />
            <form action="login.do" method="post" accept-charset="utf-8">
                <input class="input" type="text" placeholder="email" name="email" />
                <input class="input" type="password" placeholder="password" name="password"/>
                <p><input type="submit" value="Login"></p>
            </form>
            <span class="content-text">Need an account? &nbsp; <a href="register.do">Register</a></span>
        </div>
    </div>
	
	<jsp:include page="footer.jsp" />
 
</body>
</html>