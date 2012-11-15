		    		<h2>Create a New List</h2>
	    			<form action="newList.do" method=POST>
		    			<label>List Name:</label><br />
		    			<input type="text" name="listName" placeholder="Name"/><br /><br />
		    			<label>Description:</label><br/>
		    			<textarea rows="5" cols="50" name="description"></textarea><br /><br />
		    			<label>Privacy Setting</label><br />
		    			<input type="radio" name="privacySetting" value="Public">Public mode<br>
						<input type="radio" name="privacySetting" value="Private">Private mode<br><br>
						In public mode, a url will be provided to share with other users.  <br>
						In private mode, you will be able to enter specific email address you want to share the list with.<br>
		    			<input type="submit" value="Submit" />
		    		</form>