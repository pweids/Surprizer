<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="error-list.jsp" />

<form action="searchLists.do" method="POST">
	<label>Search:</label><br />
	<input type="text" name="searchString" placeholder="Name or Email" required/>
	<br /><br />
	<input type="submit" value="Search" />
</form>

<c:if test="${!empty(searchResults)}"> 
	<c:forEach var="list" items="${searchResults}">
		<a href="viewList.do?list=${list.listId}">${list.listName}</a>  ${list.owner} <br>
	    ${list.numItems} items <br>
	    <a href="followList.do?list=${list.listId}">Follow this list</a>
		<br /> <br /> 
	</c:forEach>
</c:if>


<a href="yourLists.do">Go Back</a>