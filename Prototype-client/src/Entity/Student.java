package Entity;


import Entity.User;
/**
 * This class hold Student information from the DB.
 * @author Barak
 *
 */
public class Student extends User 
{
  float GPA;
	//-------------------------------------------------//
	/**
	 * Create student
	 * @author Barak
	 * @return Student
	 * 
	 */
  public Student(String userId, String SID,String userName, String userPSW, String Type, String Email,float GPA,String b)
	{
		super(userId, userName,userPSW,Type,Email,b);
		
		this.GPA=GPA;
	}
	//-------------------------------------------------//
	
	/**
	 * get GPA..
	 * @author Barak
	 * @return float
	 * 
	 */
	public float GetGPA()
	{
		return this.GPA;
	}
	/**
	 * set email.
	 * @author Barak
	 * @param float
	 *@return void
	 */
	//--------------------------------------------------//
	public void SetEmail(float GPA)
	{
		this.GPA=GPA;
	}
	//--------------------------------------------------//
}

