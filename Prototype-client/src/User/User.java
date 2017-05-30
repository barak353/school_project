package User;

public class User {

	private int UserId;
	private String UserName;
	private String UserPassword;
	private int status;
	private String Email;
	private static User currentLoggedIn=null;
	//-------------------------------------------------------------------//
	public User(int ID, String UName, String Upssw, int s, String E)
	{
		UserId=ID;
		UserName=UName;
		UserPassword=Upssw;
		status=s;
		Email=E;
	}
	
	public static void setCurrentLoggedIn(User currentLoggedIn){
		User.currentLoggedIn = currentLoggedIn;
	}
	
	
	public static User getCurrentLoggedIn(){
		return currentLoggedIn;
	}
	//-------------------------------------------------------------------//
	public int GetID()
	{
		return this.UserId;
	}
	//-------------------------------------------------------------------//
	public String GetUserName()
	{
		return this.UserName;
	}
	//-------------------------------------------------------------------//
	public String GetUserPassword()
	{
		return this.UserPassword;
	}
	//-------------------------------------------------------------------//
	public int Getstatus()
	{
		return this.status;
	}
	//-------------------------------------------------------------------//
	public String GetEmail()
	{
		return this.Email;
	}
	//-------------------------------------------------------------------//
	public void SetEmail(String E)
	{
		this.Email=E;
	}
	//-------------------------------------------------------------------//
	public void SetUserId(int UID)
	{
		this.UserId=UID;
	}
	//-------------------------------------------------------------------//
	public void SetUserName(String UName)
	{
		this.UserName=UName;
	}
	//-------------------------------------------------------------------//
	public void SetUserPassword(String pssw)
	{
		this.UserPassword=pssw;
	}
	//-------------------------------------------------------------------//
	public void SetStatus(int s)
	{
		this.status=s;
	}
	//-------------------------------------------------------------------//
}
