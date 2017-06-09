package Entity;

public class Teacher extends User {
	

	private String MaxHours;
	
	
	//---------------------------------//
	public Teacher (String userId, String userName, String userPSW, String Type, String Email, String Max)
	{
		super(userId,userName,userPSW,Type,Email);
		MaxHours=Max;
	}
	
	
	
	
}
