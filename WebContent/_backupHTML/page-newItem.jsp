<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" />
<a href="editUser.do" >Edit user</a><br>
<a href="logout.do" >Logout</a><br>

		    		<h2>Create a New Item</h2>
	    			<form action="newItem.do" method=POST>
	    				<input type="hidden" name="listId" value="${list.listId}"/>
	    				<input type="hidden" name="userId" value="${user.userId}"/>
		    			<label>Item Name:</label><br />
		    			<input type="text" name="name" placeholder="Item name"/><br /><br />
		    			<label>Description:</label><br/>
		    			<textarea rows="5" cols="50" name="description"></textarea><br /><br />
		    			<label>Price:</label><br />
		    			<input type="text" name="price" placeholder="Price"/><br /><br />
		    			<label>Web URL:</label><br />
		    			<input type="text" name="url" placeholder="URL"/><br /><br />
		  
		    			<input type="submit" value="Submit" />
		    		</form>
		    		
		    		<br>

<a href="yourLists.do">Go Back</a>