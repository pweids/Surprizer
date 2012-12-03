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
            <span class="content-text">Register for Surprizer</span>
            <jsp:include page="error-list.jsp" />
            <c:if test="${empty(form)}">
    			<form action="register.do" method=POST accept-charset="utf-8">
	    			<input type="text" name="name" placeholder="name" required/>
	    			<input type="email" name="email" placeholder="email" required/>
		    		<input type="password" name="password" placeholder="password" required/>
		    		<input type="password" name="passwordConfirm" placeholder="confirm password" required/>
	    			<p><input type="submit" value="Register" /></p>
	    		</form>
	   		 </c:if>
    		<c:if test="${!empty(form)}">
    			<form action="register.do" method=POST accept-charset="utf-8">
	    			<input type="text" name="name" value="${form.name}" required/>
	    			<input type="email" name="email" value="${form.email}" required/>
		    		<input type="password" name="password" value="" required/>
		    		<input type="password" name="passwordConfirm" value="" required/>
	    			<p><input type="submit" value="Register" /></p>
	    		</form>
	   		 </c:if>
        </div>
    </div>
	
	<jsp:include page="footer.jsp" />
 
</body>
</html>