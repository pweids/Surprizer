<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" /><br>
<h2>${user.name}</h2>
<a href="editUser.do" >Edit user</a><br>
<a href="logout.do" >Logout</a><br>
<a href="newList.do">Create a new list</a>
<br /><br />
<h3>YOUR LISTS</h3>
<c:if test="${empty(UserLists)}"> You haven't create any lists yet. </c:if>
<c:forEach var="GiftList" items="${UserLists}">
	<a href="viewList.do?list=${GiftList.listId}">${GiftList.listName}</a>&nbsp;&nbsp;&nbsp;${GiftList.numItems} items<br /> 
	${GiftList.description}<br>
	<a href="editList.do?list=${GiftList.listId}">Edit List</a>&nbsp;&nbsp;&nbsp;<a href="deleteList.do?list=${GiftList.listId}">Delete List</a>
	<br /><br />
</c:forEach>
<hr>
<h3>LISTS YOU ARE FOLLOWING</h3>
<c:if test="${empty(SharedLists)}"> You haven't followed any lists yet.<br /> </c:if>
<c:forEach var="GiftList" items="${SharedLists}">
	<a href="viewList.do?list=${GiftList.listId}">${GiftList.listName}</a>&nbsp;&nbsp;&nbsp;${GiftList.owner}&nbsp;&nbsp;&nbsp;${GiftList.numItems} items<br /> 
	
	${GiftList.description}<br>
	<a href="unfollowList.do?list=${GiftList.listId}">Unfollow List</a>
	<br /> <br>  <br>
</c:forEach>
<a href="searchLists.do">Find other lists to follow</a>