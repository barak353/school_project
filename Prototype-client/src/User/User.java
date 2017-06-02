package User;

public class User {

	private String userId;
	private String userName;
	private String userPSW;
	private char Type;
	private String Email;
	private static User currentLoggedIn=null;
	//-------------------------------------------------------------------//
	public User(String userId, String userName, String userPSW, String Type, String Email)
	{
		this.userId = userId;
		this.userName = userName;
		this.userPSW = userPSW;
		this.Type = Type.charAt(0);
		this.Email=Email;
	}
	
	public static void setCurrentLoggedIn(User currentLoggedIn){
		User.currentLoggedIn = currentLoggedIn;
	}
	
	//-------------------------------------------------------------------//
	public static User getCurrentLoggedIn(){
		return currentLoggedIn;
	}
	//-------------------------------------------------------------------//
	public String GetID()
	{
		return this.userId;
	}
	//-------------------------------------------------------------------//
	public String GetUserName()
	{
		return this.userName;
	}
	//-------------------------------------------------------------------//
	public String GetUserPassword()
	{
		return this.userPSW;
	}
	//-------------------------------------------------------------------//
	public char Getstatus()
	{
		return this.Type;
	}
	//-------------------------------------------------------------------//
	public String GetEmail()
	{
		return this.Email;
	}
	//-------------------------------------------------------------------//
	public void SetEmail(String Email)
	{
		this.Email=Email;
	}
	//-------------------------------------------------------------------//
	public void SetUserId(String userId)
	{
		this.userId=userId;
	}
	//-------------------------------------------------------------------//
	public void SetUserName(String userName)
	{
		this.userName = userName;
	}
	//-------------------------------------------------------------------//
	public void SetUserPassword(String pssw)
	{
		this.userPSW=pssw;
	}
	//-------------------------------------------------------------------//
	public void SetType(char Type)
	{
		this.Type=Type;
	}
	 //-------------------------------------------------------------------//
	public String toString(){
		return String.format("userID: %s, userName: %s, userPSW: %s, Type: %c, Email: %s",this.userId,this.userName,this.userPSW,this.Type,this.Email);
		
	}
}
