package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GiftList {
	int listId;
	String owner;
	String listName;
	String description;
	String privacySetting;
	String url;
	int numItems;
	
	public int getListId() 				{ return listId;		 }
	public String getOwner() 			{ return owner;			 }
	public String getListName() 		{ return listName;		 }
	public String getDescription() 		{ return description;	 }
	public String getPrivacySetting() 	{ return privacySetting; }
	public String getUrl() 				{ return url;			 }
	public int getNumItems() 			{ return numItems;			 }
	
	public void setListId(int i) 		{ listId = i; }
	public void setOwner(String s) 		{ owner = s;	}
	public void setListName(String s) 	{ listName = s; }
	public void setDescription(String s) 		{ description = s;	}
	public void setPrivacySetting(String s) {	privacySetting = s; }
	public void setUrl(String s) 		{ url = s;	}
	public void setNumItems(int i) 		{ numItems = i;	}
	
	public GiftList(){}
	
	public GiftList(String l, String d, String o, String p){
		owner = o;
		listName = l;
		description = d;
		privacySetting = p;
		if(privacySetting.equals("Public"))
			url = hash(listName);
		else url = null;
		numItems = 0;
	}
	
	private String hash(String listName) {	
		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		md.update(listName.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}
}
