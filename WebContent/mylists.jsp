<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" />
<body>
<div id="header">
       <h2 class="logo main">
        SURPRIZER
       </h2>
       <div id="page">My Lists</div>
       <div id="user">
        <span id="username">${user.name}</span>
        <span id="actions"><a href="logout.do">Logout</a></span>
        <span id="actions"><a href="editUser.do">User Settings</a></span>
       </div>
	</div>
	
	<div id="content">
	   <form id="newList" name="newList" action="newList.do" method="POST" accept-charset="utf-8">
	           <input type="text" placeholder="name" name="listName" required />
	           <input type="text" placeholder="description" name="description" /><br />
	           <span class='newListRadioText'>Privacy setting: </span>
	           <input type="radio" name="privacySetting" value="Public" checked><span class="newListRadioText">Public</span>
	           <input type="radio" name="privacySetting" value="Private"><span class="newListRadioText">Private</span>
	           <input type="submit" />
	       </form>
	       <button id="newListButton">+ Create New List</button>
	       
	       
	       <c:if test="${empty(UserLists)}"> <p>You haven't created any lists yet.</p> </c:if>
	       <c:forEach var="gl" items="${UserLists}">
	       <div class="list">
	           <span class="numItems">${gl.numItems} Items</span>
	           <h2 class="title">${gl.listName}</h2>
	           <span class="desc">${gl.description}</span><br /><br />
	           <span class="date">Created TBD</span>
	           <p class="listActions"><a href="viewList.do?list=${gl.listId}">Edit list</a>&nbsp;<a href="" class='delete'>Delete list<span class="id" style="display:none">${gl.listId }</span> </a></p>
	           
	       </div>     
	       </c:forEach>
	       
	     
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
				           '<p class="listActions"><a href="viewList.do?list=">Edit list</a>&nbsp;<a href="deleteList.do?list=">Delete list</a></p>' + 
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
			console.log()
			$(this).parent().parent().hide();
			
			var req = new XMLHttpRequest();
			req.open('GET', '/Surprizer/deleteList.do?list=' + $('.id', $(this)).html());
			req.send();
			
		});
		
	</script>
 
</body>
</html>