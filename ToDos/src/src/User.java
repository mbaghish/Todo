package src;


public class User {
    private String username;
    private String password;
    private int externkey;
    private boolean readOnly; // New property to indicate read-only access

    public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
//	User (String username, String password)
//    {
//        this.username = username;
//        this.password = password;
//    }

	User (String username, String password, int externkey)
    {
        this.username = username;
        this.password = password;
        this.externkey = externkey;
    }

    public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String getUsername() {return username;}
    String getPassword() {return password;}
 // Getter for read-only property
    public boolean hasReadOnlyAccess() {
        return readOnly;
    }

	public int getExternkey() {
		return externkey;
	}

	public void setExternkey(int externkey) {
		this.externkey = externkey;
	}

}
