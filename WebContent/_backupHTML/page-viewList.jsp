<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" /><br>
<h2>${list.listName}</h2>
<h3>${GiftList.description}</h3>

<c:choose>
<c:when test="${list.owner == user.name}">
<a href="editList.do?list=${list.listId}">Edit List</a><br>
<a href="deleteList.do?list=${list.listId}">Delete List</a>
	
</c:when>
<c:when test="${following == 'true'}">
   <a href="unfollowList.do?list=${list.listId}">Unfollow this list</a>
</c:when>
<c:otherwise>
	<a href="followList.do?list=${list.listId}">Follow this list</a>
</c:otherwise>
</c:choose>

<br>
<c:if test="${list.owner == user.name}">
	<a href="newItem.do">Add a new item</a><br/>
	
	<c:forEach var="item" items="${items}">
		<h3>${item.name}</h3>
		<c:if test="${!empty(item.description)}">${item.description}<br></c:if>
		<c:if test="${!empty(item.price)}">${item.price}<br></c:if>
		<c:if test="${!empty(item.url)}">${item.url}<br></c:if>
		<a href="editItem.do?item=${item.itemId}">Edit Item</a>  <a href="deleteItem.do?item=${item.itemId}">Delete Item</a>	
		<br>
		<br>
	</c:forEach>
</c:if>

<c:if test="${list.owner != user.name}">
	<c:forEach var="item" items="${items}">
		<h3>${item.name}</h3>
		<c:if test="${!empty(item.description)}">${item.description}<br></c:if>
		<c:if test="${!empty(item.price)}">${item.price}<br></c:if>
		<c:if test="${!empty(item.url)}">${item.url}<br></c:if>
		<c:if test="${item.userId == 0}"> <a href="markItem.do?item=${item.itemId}">Mark item as bought</a></c:if>
		<c:if test="${item.userId == user.userId}">You've marked this item as purchased.  <a href="unmarkItem.do?item=${item.itemId}">Unmark item as bought</a></c:if>
		<c:if test="${(item.userId != user.userId) && (item.userId != 0)}">Item marked as bought by another user<br></c:if>
		<br>
		<br>
	</c:forEach>
</c:if>

<a href="yourLists.do">Go Back</a>