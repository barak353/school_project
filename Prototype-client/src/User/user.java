package User;

public class user {

	private int UserId;
	private String UserName;
	private int UserPassword;
	private int status;
	private String Email;
	//-------------------------------------------------------------------//
	public user(int ID, String UName, int Upssw, int s, String E)
	{
		UserId=ID;
		UserName=UName;
		UserPassword=Upssw;
		status=s;
		Email=E;
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
	public int GetUserPassword()
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
	public void SetUserPassword(int pssw)
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
