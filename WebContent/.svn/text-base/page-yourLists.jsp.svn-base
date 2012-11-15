<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="error-list.jsp" />

<a href="logout.do" >Logout</a><br>
<a href="newList.do">Create a new list</a>
<br /><br />
<c:forEach var="GiftList" items="${UserLists}">
	<a href="viewList.do&list=${GiftList.listId}">${GiftList.listName}</a>${GiftList.owner} ${GiftList.numItems} items<br /> 
	<a href="editList.do&list=${GiftList.listId}">Edit List</a><br />
	<a href="shareList.do&list=${GiftList.listId}">Share List</a><br />
	<a href="deleteList.do&list=${GiftList.listId}">Delete List</a><br />			
<br>
<br>
				</c:forEach>