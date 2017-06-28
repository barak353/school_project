package Entity;

public class User {

	private String userId;
	private String userName;
	private String userPSW;
	private char Type;
	private String Email;
	private static User currentLoggedIn=null;
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public User(String userId, String userName, String userPSW, String Type, String Email, String Isclocked)
	{
		this.userId = userId;
		this.userName = userName;
		this.userPSW = userPSW;
		this.Type = Type.charAt(0);
		this.Email=Email;
	}
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public static void setCurrentLoggedIn(User currentLoggedIn){
		User.currentLoggedIn = currentLoggedIn;
	}
	
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public static User getCurrentLoggedIn(){
		return currentLoggedIn;
	}
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public String GetID()
	{
		return this.userId;
	}
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public String GetUserName()
	{
		return this.userName;
	}
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public String GetUserPassword()
	{
		return this.userPSW;
	}
	//-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public char Getstatus()
	{
		return this.Type;
	}
	//-------------------------------------------------------------------//
	/**
	 * get user email
	 * @author Barak
	 */
	public String GetEmail()
	{
		return this.Email;
	}
	//-------------------------------------------------------------------//
	/**
	 * set user email
	 * @author Barak
	 */
	public void SetEmail(String Email)
	{
		this.Email=Email;
	}
	//-------------------------------------------------------------------//
	/**
	 * set user if
	 * @author Barak
	 */
	public void SetUserId(String userId)
	{
		this.userId=userId;
	}
	//-------------------------------------------------------------------//
	/**
	 * set user name
	 * @author Barak
	 */
	public void SetUserName(String userName)
	{
		this.userName = userName;
	}
	//-------------------------------------------------------------------//
	/**
	 * set user password
	 * @author Barak
	 */
	public void SetUserPassword(String pssw)
	{
		this.userPSW=pssw;
	}
	//-------------------------------------------------------------------//
	/**
	 * set type
	 * @author Barak
	 */
	public void SetType(char Type)
	{
		this.Type=Type;
	}
	 //-------------------------------------------------------------------//
	/**
	 * Hold users information.
	 * @author Barak
	 */
	public String toString(){
		return String.format("userID: %s, userName: %s, userPSW: %s, Type: %c, Email: %s",this.userId,this.userName,this.userPSW,this.Type,this.Email);
		
	}
}
