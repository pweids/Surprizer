package databeans;

import java.net.MalformedURLException;

import org.apache.tomcat.util.net.URL;


public class Item {
	int itemId;
	int listId;
	String name;
	String description;
	String price;
	String url;
	String hostURL;
	int userId; //purchased by
//BLOB IMAGE

	public Item(){}
	
	public Item(int itemlist, String itemName, String itemDescription, String itemPrice, String itemUrl){
		listId = itemlist; 
		name = itemName;
		description = itemDescription;
		price = itemPrice;
		url = itemUrl;
			try {
				hostURL = new URL(url).getHost();
			} catch (MalformedURLException e) {
				System.err.println("bad host");
				hostURL = null;
			}
	}

	public int getItemId() { return itemId; }
	public int getListId() { return listId; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public String getPrice() { return price; }
	public String getUrl() { return url; }
	public int getUserId() { return userId; }
	
	public String getHostURL() throws MalformedURLException { return hostURL; }
	public void setHostURL(String hostURL) { this.hostURL = hostURL; }

	public void setItemId(int itemId) { this.itemId = itemId; }
	public void setListId(int listId) { this.listId = listId; }
	public void setName(String name) { this.name = name; }
	public void setDescription(String description) { this.description = description; }
	public void setPrice(String price) { this.price = price; }
	public void setUrl(String url) { this.url = url; }
	public void setUserId(int userId) { this.userId = userId; }
}
