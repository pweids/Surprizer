<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" />
	<style type="text/css">
		#content form {
			color:white;
		}
		label {
			color: #eee;
			font-weight: bold;
		}
	</style>
<body>
<div id="header">
       <h2 class="logo main">
        SURPRIZER
       </h2>
       <div id="page">Edit "${giftlist.listName }" settings</div>
       <div id="user">
        <span id="username">${user.name}</span>
        <span id="actions"><a href="yourLists.do">My Lists</a> <a href="logout.do">Logout</a></span>
       </div>
	</div>
	
	<div id="content">
		<br />
	   <form action="editList.do?list=${giftlist.listId}" method="GET">
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
				<br />
				In public mode, other users will be able to search for and follow your list.  <br />
				In private mode, only you will be able to see your list.<br /><br />
				<input type="submit" value="Submit" />
			</form>
	       
	     
	</div>
	
	<jsp:include page="footer.jsp" />
</body>
</html>