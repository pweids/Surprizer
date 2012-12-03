
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../error-list.jsp" />

    		<h2>Register to get started!!</h2>
    		<c:if test="${empty(form)}">
    			<form action="register.do" method=POST>
	    			<label>Name:</label><br />
	    			<input type="text" name="name" placeholder="Name" required/>
	    			<br /><br />
	    			<label>Email:</label><br />
	    			<input type="email" name="email" placeholder="Email" required/>
	    			<br /><br />
	    			<label>Password:</label><br />
		    		<input type="password" name="password" placeholder="Password" required/><br />
		    		<label>Confirm Password:</label><br />
		    		<input type="password" name="passwordConfirm" placeholder="Confirm password" required/>
	    			<br /><br />
	    			<input type="submit" value="Register" />
	    		</form>
	   		 </c:if>
	   		 
    		<c:if test="${!empty(form)}">
    			<form action="register.do" method=POST>
	    			<label>Name:</label><br />
	    			<input type="text" name="name" value="${form.name}" required/>
	    			<br /><br />
	    			<label>Email:</label><br />
	    			<input type="email" name="email" value="${form.email}" required/>
	    			<br /><br />
	    			<label>Password:</label><br />
		    		<input type="password" name="password" value="" required/><br />
		    		<label>Confirm Password:</label><br />
		    		<input type="password" name="passwordConfirm" value="" required/>
	    			<br /><br />
	    			<input type="submit" value="Register" />
	    		</form>
	   		 </c:if>

