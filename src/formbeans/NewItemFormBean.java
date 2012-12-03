package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mybeans.form.FormBean;

public class NewItemFormBean extends FormBean {
	private String name;
	private String description;
	private String price;
	private String url;
	
	public String getName() { return name; }
	public String getDescription() { return description; }
	public String getPrice() { return price; }
	public String getUrl() { return url; }
	
	public void setName(String s) { name = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setDescription(String s) { description = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setPrice(String s) { price = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setUrl(String s) { url = StringEscapeUtils.escapeHtml4(s.trim()); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (name == null || name.length() == 0) {
			errors.add("A name is required");
		}
		
		//Other fields not required
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
	
	public boolean isPresent() {
		if (name != null) return true;
		return false;
	}


}
