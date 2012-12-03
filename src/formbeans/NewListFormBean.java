package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mybeans.form.FormBean;

public class NewListFormBean extends FormBean {
	private String listName;
	private String description;
	private String privacySetting;	
	
	public String getListName() { return listName; }
	public String getDescription() { return description; }
	public String getPrivacySetting() { return privacySetting; }

	public void setListName(String s) { listName = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setDescription(String s) { description = StringEscapeUtils.escapeHtml4(s.trim()); }
	public void setPrivacySetting(String s) { privacySetting = StringEscapeUtils.escapeHtml4(s.trim()); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (listName == null || listName.length() == 0) {
			errors.add("A GiftList Name is required");
		}
		
		//description is not required
		
		if (privacySetting == null || privacySetting.length() == 0) {
			errors.add("A Privacy Setting is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
	
	public boolean isPresent() {
		if (listName != null) return true;
		return false;
	}

	
}
