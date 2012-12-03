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
       <div id="page">Edit ${item.name} settings</div>
       <div id="user">
        <span id="username">${user.name}</span>
        <span id="actions"><a href="myLists.do">My Lists</a> &nbsp; <a href="editUser.do">User Settings</a> &nbsp; <a href="logout.do">Logout</a></span>
       </div>
	</div>
	
	<div id="content">
		<div id="content-box">
			<span class="content-text">Edit Item</span>
			
			<jsp:include page="error-list.jsp" />
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
    			<input type="button" id="cancel" value="Cancel" />
		    </form>
        </div>
		
	       
	     
	</div>
	
	<jsp:include page="footer.jsp" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#cancel').click( function() {
				window.history.back();
			});
		});
	</script>
</body>
</html>