<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="error-list.jsp" />
<a href="editUser.do" >Edit user</a><br>
<a href="logout.do" >Logout</a><br>
<h2>Edit List Information</h2>

		    		<h2>Create a New List</h2>
	    			<form action="editList.do?list=${giftlist.listId}" method=POST>
		    			<label>List Name:</label><br />
		    			<input type="text" name="listName" value="${giftlist.listName}"/><br /><br />
		    			<label>Description:</label><br/>
		    			<textarea rows="5" cols="50" name="description">${giftlist.description}</textarea><br /><br />
		    			<label>Privacy Setting</label><br />
		    			
		    			<c:if test="${!empty(giftlist)}">
			    			<c:choose>  
								<c:when test="${giftlist.privacySetting == 'Public'}">
									<input type="radio" name="privacySetting" value="Public" checked="checked">Public<br>
			    					<input type="radio" name="privacySetting" value="Private">Private<br>
								</c:when>
								<c:when test="${giftlist.privacySetting == 'Private'}">
									<input type="radio" name="privacySetting" value="Public" >Public<br>
			    					<input type="radio" name="privacySetting" value="Private" checked="checked">Private<br>
								</c:when>
								<c:otherwise>
									<input type="radio" name="privacySetting" value="Public" >Public<br>
			    					<input type="radio" name="privacySetting" value="Private">Private<br>
								</c:otherwise>
							</c:choose>
						</c:if>
		    			In public mode, other users will be able to search for and follow your list.  <br>
						In private mode, only you will be able to see your list.<br>
		    			<input type="submit" value="Submit" />
		    		</form>

<a href="yourLists.do">Go Back</a>