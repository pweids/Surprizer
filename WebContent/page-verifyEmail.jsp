<jsp:include page="error-list.jsp" />
Verify page
<form method=POST action="verify.do">
	<input type="hidden" name="reverify" value="true">
	<input type="submit" value="Re-send verification email">
</form>
