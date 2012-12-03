<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" />
<style type="text/css">
h2 {
font-size:2em; font-weight:bold; color:#eee; text-decoration:underline;
}
</style>
<body>
<div id="header">
       <h2 class="logo main">
        SURPRIZER
       </h2>
       <div id="page">Search results for "${searchString}"</div>
       <div id="user">
        <span id="username">${user.name}</span>
        <span id="actions"><a href="logout.do">Logout</a></span>
       </div>
	</div>
	
	<div id="content">
	       <c:forEach var="gl" items="${searchResults}">
	       <div class="list">
	           <span class="numItems">${gl.numItems} Items</span>
	           <h2 class="title"><a href="viewList.do?list=${gl.listId}">${gl.listName}</a></h2>
	           <span class="desc">${gl.description}</span><br /><br />
	           <span class="date">Created by ${gl.owner}</span>
	           <p class="listActions"><a href="followList.do?list=${gl.listId}">Follow this list</a></p>
	           
	       </div>     
	       </c:forEach>
	       
			<p style="color:white">Search for more lists.</p>
	       <form action="searchLists.do" method="GET">
       			<input type="text" placeholder="Name or email" name="searchString" />
       			<input type="submit" value="Search" />
       		</form>
	     
	</div>
	
	<jsp:include page="footer.jsp" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#newListButton').click(function (e) {
			e.preventDefault();
			var oForm = document.forms['newList'];
			
			var pairs = [];
			pairs.push('listName='+oForm.elements['listName'].value);
			pairs.push('description='+oForm.elements['description'].value);
			pairs.push('privacySetting='+oForm.elements['privacySetting'].value);
			
			
			var stateChanger = function() {
				$('#content').append('<div class="list"><span class="numItems">0 Items</span>'+
				           '<h2 class="title">'+oForm.elements['listName'].value+'</h2>' +
				           '<span class="desc">${gl.description}</span><br /><br />' +
				           '<span class="date">Created '+(new Date(Date.now())).toDateString()+'</span>' +
				           '<p class="listActions"><a href="viewList.do?list=">Edit list</a>&nbsp;<a href="" class="delete">Delete list</a></p>' + 
				       '</div>');
				oForm.elements['listName'].value = '';
				oForm.elements['description'].value = '';
				
			};
			
			var oReq = new XMLHttpRequest();
			oReq.open('POST', '/Surprizer/newList.do');
			oReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			
		
			
			oReq.onreadystatechange = function() {
				if (oReq.readyState === 4){
					stateChanger();
					console.log(this.responseXML);
				}
			};
			
			oReq.send(pairs.join('&'));
		});
		
		$('.delete').click(function (e) {
			e.preventDefault();
			$(this).parent().parent().hide();
			
			var req = new XMLHttpRequest();
			req.open('GET', '/Surprizer/deleteList.do?list=' + $('.id', $(this)).html());
			req.send();
			
		});
		$('.unfollow').click(function (e) {
			e.preventDefault();
			$(this).parent().parent().hide();
			
			var req = new XMLHttpRequest();
			req.open('GET', '/Surprizer/unfollowList.do?list=' + $('.id', $(this)).html());
			req.send();
			
		});
		
	</script>
 
</body>
</html>