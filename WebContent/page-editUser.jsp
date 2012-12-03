<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" />
<body>
<div id="header">
       <h2 class="logo main">
        SURPRIZER
       </h2>
       
       <div id="user">
        	<span id="username">${user.name}</span>
        	<span id="actions"><a href="logout.do">Logout</a></span>
    </div>
</div>

<div id="content">	

	<div id="content-box">
            <span class="content-text">Edit User Settings</span>
            
            <jsp:include page="error-list.jsp" />
    		<form action="editUser.do" method=POST accept-charset="utf-8">
	    		<p>Name: <input type="text" name="name" value="${user.name}"/></p>
		    	<p>Password: <br><input type="password" name="password" placeholder="Password"/><br />
		    	   <input type="password" name="passwordConfirm" placeholder="Confirm password"/></p>
	    		<p><input type="submit" value="Confirm" /></p>
	    	</form> 
	    	<a href="yourLists.do">Go Back</a>
        </div>
        
        

</div>
	
	<jsp:include page="footer.jsp" />
	</body>
	</html>