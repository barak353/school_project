package Entity;

public class Teacher extends User {
	

	private String MaxHours;
	
	
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
	public String GetHours()
	{
		return this.MaxHours;
	}
	//---------------------------------//
}
