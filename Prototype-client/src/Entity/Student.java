package Entity;


import Entity.User;

public class Student extends User 
{
  float GPA;
	//-------------------------------------------------//
	public Student(String userId, String SID,String userName, String userPSW, String Type, String Email,float GPA)
	{
		super(userId, userName,userPSW,Type,Email);
		
		this.GPA=GPA;
	}
	//-------------------------------------------------//
	
	
	public float GetGPA()
	{
		return this.GPA;
	}
	//-------------------------------------------------------------------//
	public void SetEmail(float GPA)
	{
		this.GPA=GPA;
	}
	//-------------------------------------------------------------------//
}

