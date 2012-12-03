package databeans;

public class UsersLists {
	int userListId;
	int userId;
	int listId;
	
	public int getUserListId() { return userListId; }
	public int getUserId() { return userId; }
	public int getListId() { return listId; }
	
	public void setUserListId(int userListId) { this.userListId = userListId; }
	public void setUserId(int userId) {	this.userId = userId; }
	public void setListId(int listId) {	this.listId = listId; }
	
	public UsersLists(){}
	
	public UsersLists(int uId, int lId){
		userId = uId;
		listId = lId;
	}
	
	
}
