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
            <div id="page">
                ${list.listName}
            </div>
            <div id="user">
                <span id="username">${user.name}</span> <span id="actions"><a href="yourLists.do">My lists</a> &nbsp; <a href="editUser.do">User Settings</a> &nbsp; <a href="logout.do">Logout</a></span>
            </div>
        </div>
        
        <div id="content">
			
			<c:if test="${list.owner == user.name}">
           		<div id="editList">
                	<p>
                    	Add, edit, or delete items to perfect and keep your list up to date.
                	</p>
            	</div><button id="newItem">+ Add New Item</button>
				<!-- <a href="deleteList.do?list=${list.listId}">Delete List</a> -->
            	
            	<div id="itemAdder" class="list" style="display:none;">
                <div class="clear"></div>
                <h2 class="title">
                    <input type="text" id="newTitle" placeholder="Title" style="width: 75%; padding:.5em;"/>
                </h2><span class="price"><input type="text" id="price" placeholder="Optional price"/></span> 
                <input type="text" id="link" placeholder="Optional: Link" />
                <textarea name="" cols="60" rows="5" id="newDesc" placeholder="Optional description"></textarea>
                
                <p class="listActions">
                    <button id="add">Add</button>&nbsp;<button id="cancel">Cancel</button>
                </p>
           		</div>
           		
				<div id="sortable">
				<c:forEach var="item" items="${items}">
					<div class="list">
						<img src="images/box.jpeg" alt='${item.name}'>
						<div class="clear"></div>
						<h2 class="title">${item.name}</h2>
						<span class="price">${item.price}</span>
						<!--  Need to truncate the site URL --> 
						<a href="${item.url}" class="site">${item.hostURL}</a>
						<p class="desc">${item.description}</p>
						<p class="listActions">
							
							<a class="edit" href="">Edit item<span class="id"  style="display:none">${item.itemId}</span></a> &nbsp; 
							<a class="delete" href="">Delete item<span class="id"  style="display:none">${item.itemId}</span></a>
						</p>
					</div>
				</c:forEach>
			</c:if>
		
		<c:if test="${list.owner != user.name}">
			<c:choose>
				<c:when test="${following == 'true'}">
				   <a href="unfollowList.do?list=${list.listId}">Unfollow this list</a>
				</c:when>
				<c:otherwise>
					<a href="followList.do?list=${list.listId}">Follow this list</a>
				</c:otherwise>
			</c:choose>
			<div>
			<c:forEach var="item" items="${items}">
			<div class="list">
				<img src="images/box.jpeg" alt=${item.name}>
				<div class="clear"></div>
				<h2 class="title">${item.name}</h2>
				<span class="price">${item.price}</span>
				<!--  Need to truncate the site URL --> 
				<a href="${item.url}" class="site">${item.url}</a>
				<p class="desc">${item.description}</p>
				<c:if test="${item.userId == 0}"> <a href="markItem.do?item=${item.itemId}">Mark item as bought</a></c:if>
				<c:if test="${item.userId == user.userId}">You've marked this item as purchased.  <a href="unmarkItem.do?item=${item.itemId}">Unmark item as bought</a></c:if>
				<c:if test="${(item.userId != user.userId) && (item.userId != 0)}">Item marked as bought by another user<br></c:if>
			</div>
			</c:forEach>
		</c:if>   
                
            </div>
            
        </div>
		<jsp:include page="footer.jsp" />
        
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript">
        </script>
        <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js" type="text/javascript">
        </script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#sortable").sortable();
                
                $("#newItem").click(function() {
                    if (!$("#itemAdder").is(":visible"))
                    $("#itemAdder").slideToggle();
                })
                
                $("#cancel").click(function() {
                    if ($("#itemAdder").is(":visible"))
                    $("#itemAdder").slideToggle();
                });
                
                function adderCallback() {
                	var item = $(document.createElement("div")).addClass("list");
                    var ilink = $("#imglink").val() || "images/box.jpeg";
                    var plink = $("#link").val();
                    item.append('<img src="'+ilink+'" alt="Image">');
                    item.append('<div class="clear"></div>');
                    item.append('<h2 class="title">'+$("#newTitle").val()+'</h2>');
                    item.append('<span class="date">'+$('#price').val()+'</span>');
                    item.append('<a href="'+plink+'" class="site">'+plink.substring(0,20)+'</a>');
                    item.append('<p class="desc">'+$('#newDesc').val()+'</p>');
                    var la = $(document.createElement("p")).addClass("listActions");
                    la.append('<a class="edit" href="">Edit item</a>');
                    la.append('<a class="delete" href="">Delete item</a>');
                    item.append(la);
                    $("#sortable").append(item);
                    
                    $.each($("#itemAdder").find('*'), function(index, value) { if (value.tagName === "INPUT" || value.tagName === "TEXTAREA") $(value).val("");})
                    $("#itemAdder").slideToggle();
                }
                
                $("#add").click(function() {
                    var el = $('#itemAdder');
                    if ($("#newTitle").val() !== ""){
                    	var oForm = document.forms['newList'];
            			
            			var pairs = [];
            			
            			pairs.push('listId='      + ${list.listId});
            			pairs.push('userId='      + ${user.userId});
            			pairs.push('name='        + $('#newTitle').val());
            			pairs.push('price='       + $('#price').val());
            			pairs.push('url='         + $('#link').val());
            			pairs.push('description=' + $('#newDesc').val());
            				
            			};
            			
            			var oReq = new XMLHttpRequest();
            			oReq.open('POST', '/Surprizer/newItem.do');
            			oReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            			
            		
            			
            			oReq.onreadystatechange = function() {
            				if (oReq.readyState === 4){
            					adderCallback();
            				}
            			};
            			
            			oReq.send(pairs.join('&'));
            			
                    	
                });
                
                $(".delete").click(function(e) {
                    e.preventDefault();
                    var req = new XMLHttpRequest();
                    req.open('GET', 'deleteItem.do?item=' + $('.id', $(this)).html());
                    req.send();
                    $(this).parent().parent().hide();
                })
                
                $(".edit").click(function(e) {
                    e.preventDefault();
                    var req = new XMLHttpRequest();
                    req.open('GET', 'editItem.do?item=' + $('.id', $(this)).html());
                    req.send();
                    $(this).parent().parent().hide();
                    
                });
            });
            
        </script>
</body>
</html>