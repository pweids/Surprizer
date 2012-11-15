    		<c:if test="${!empty(form)}">
    			<form action="editUser.do" method=POST>
	    			<label>Name:</label><br />
	    			<input type="text" name="firstName" value="${form.name}" required/>
	    			<br /><br />
	    			<label>Password:</label><br />
		    		<input type="password" name="password" value="" required/><br />
		    		<label>Confirm Password:</label><br />
		    		<input type="password" name="passwordConfirm" value="" required/>
	    			<br /><br />
	    			<input type="submit" value="Register" />
	    		</form>
	   		 </c:if>
