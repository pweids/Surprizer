<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" />
<a href="editUser.do" >Edit user</a><br>
<a href="logout.do" >Logout</a><br>

		    		<h2>Edit your Item</h2>
	    			<form action="editItem.do" method=POST>
		    			<input type="hidden" name="itemId" value="${item.itemId}"/>
	    				<input type="hidden" name="listId" value="${list.listId}"/>
	    				<input type="hidden" name="userId" value="${user.userId}"/>
		    			<label>Item Name:</label><br />
		    			<input type="text" name="name" value="${item.name}"/><br /><br />
		    			<label>Description:</label><br/>
		    			<textarea rows="5" cols="50" name="description">${item.description}</textarea><br /><br />
		    			<label>Price:</label><br />
		    			<input type="text" name="price" value="${item.price}"/><br /><br />
		    			<label>Web URL:</label><br />
		    			<input type="text" name="url" value="${item.url}"/><br /><br />
		  
		    			<input type="submit" value="Submit" />
		    		</form>
		    		
		    		<br>

<a href="viewList.do">Go Back</a>