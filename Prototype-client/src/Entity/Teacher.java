package Entity;

public class Teacher extends User {
	

	private String MaxHours;

	//---------------------------------//
	/**
	 * This class hold teacher information.
	 * @author Barak
	 */
	public Teacher (String userId, String userName, String userPSW, String Type, String Email, String Max, String b)
	{
		super(userId,userName,userPSW,Type,Email,b);
		this.MaxHours=Max;
	}
	//---------------------------------//
	/**
	 * set hours
	 * @author Barak
	 *  @return void
	 *  @param String
	 */
	public void SetHours(String Max)
	{
		this.MaxHours=Max;
	}
	//---------------------------------//
	/**
	 * get hours
	 * @author Barak
	 * @return String
	 */
	public String Gethours()
	{
		return this.MaxHours;
	}
	//---------------------------------//
}
