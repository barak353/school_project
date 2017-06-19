package Entity;

public class Teacher extends User {
	

	private String MaxHours;
<<<<<<< HEAD

	//---------------------------------//
	public Teacher (String userId, String userName, String userPSW, String Type, String Email, String Max, String b)
	{
		super(userId,userName,userPSW,Type,Email,b);
		this.MaxHours=Max;
	}
	//---------------------------------//
	public void SetHours(String Max)
	{
		this.MaxHours=Max;
	}
	//---------------------------------//
	public String Gethours()
	{
		return this.MaxHours;
=======
	
	private String tracherid;
	
	//---------------------------------//
	public Teacher (String userId, String userName, String userPSW, String Type, String Email, String Max, String b)
	{
		super(userId,userName,userPSW,Type,Email,b);
		MaxHours=Max;
	}
	//---------------------------------//
	public void SetHours(String Max)
	{
		this.MaxHours=Max;
	}
	//---------------------------------//
	public String tracherid()
	{
		return this.MaxHours;
	}
	//---------------------------------//
	public void tracherid(String tracherid)
	{
		this.tracherid=tracherid;
	}
	//---------------------------------//
	public String GetHours()
	{
		return this.tracherid;
>>>>>>> refs/remotes/origin/school-project
	}
	//---------------------------------//
}
