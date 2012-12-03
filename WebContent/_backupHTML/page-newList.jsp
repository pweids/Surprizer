<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" />
<a href="editUser.do" >Edit user</a><br>
<a href="logout.do" >Logout</a><br>

		    		<h2>Create a New List</h2>
	    			<form action="newList.do" method=POST>
		    			<label>List Name:</label><br />
		    			<input type="text" name="listName" placeholder="Name"/><br /><br />
		    			<label>Description:</label><br/>
		    			<textarea rows="5" cols="50" name="description"></textarea><br /><br />
		    			<label>Privacy Setting</label><br />
		    			<input type="radio" name="privacySetting" value="Public">Public<br>
						<input type="radio" name="privacySetting" value="Private">Private<br><br>
						In public mode, other users will be able to search for and follow your list.  <br>
						In private mode, only you will be able to see your list.<br>
		    			<input type="submit" value="Submit" />
		    		</form>
		    		
		    		<br>

<a href="yourLists.do">Go Back</a>