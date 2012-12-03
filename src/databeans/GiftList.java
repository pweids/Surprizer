package databeans;

public class GiftList {
	int listId;
	int userId;
	String owner;
	String listName;
	String description;
	String privacySetting;
	int numItems;
	
	public int getListId() 				{ return listId;		 }
	public int getUserId() 				{ return userId;		 }
	public String getOwner() 			{ return owner;			 }
	public String getListName() 		{ return listName;		 }
	public String getDescription() 		{ return description;	 }
	public String getPrivacySetting() 	{ return privacySetting; }
	public int getNumItems() 			{ return numItems;			 }
	
	public void setListId(int i) 		{ listId = i; }
	public void setUserId(int i) 		{  userId = i;		 }
	public void setOwner(String s) 		{ owner = s;	}
	public void setListName(String s) 	{ listName = s; }
	public void setDescription(String s) 		{ description = s;	}
	public void setPrivacySetting(String s) {	privacySetting = s; }
	public void setNumItems(int i) 		{ numItems = i;	}
	
	public void incNumItems() { numItems++; }
	public void decNumItems() { numItems--; }
	
	public GiftList(){}
	
	public GiftList(String l, String d, int i, String o, String p){
		listName = l;
		description = d;
		userId = i;
		owner = o;
		privacySetting = p;
		numItems = 0;
	}
}
