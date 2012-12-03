package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class User {
	private int userId;
    private String name;
    private String email;
    private String password;
	private int salt;
    private String verified;

    public int getUserId() {  return userId;  }
    public String getName() {  return name;  }
    public String getEmail() {  return email;  }
	public String getPassword() {  return password;  }
	public int getSalt() {  return salt;  }
	public String getVerified() {  return verified;  }
	
	public void setUserId(int userId) {  this.userId = userId;  }
	public void setName(String s) {  name = s; }
	public void setEmail(String s) {  email = s; }
	public void setPassword(String s) { password = s; }
	public void setSalt(int x) {  salt = x; }
	public void setVerified(String s) {  verified = s; }
    
	public User(){}
	
    public User(String n, String e, String p) {
    	name = n;
		email = e;
		password = hash(p);
		verified = hash(e);
    }
	
	public boolean checkPassword(String passwordAttempt) {
		return password.equals(hash(passwordAttempt));
		
	}
	public boolean isEmailVerified(){
		return verified.equals("Verified");
	}

	private String hash(String clearPassword) {
		if (salt == 0){ 
			salt = newSalt(); 
		}
		
		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
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

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}

}
