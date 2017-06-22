package Entity;

import java.sql.Date;
import java.util.ArrayList;

import application.QueryController;

public class Semester {
	
	private String year;
	private String type;
	private boolean status;
	private static String str;
	private static String str2;
	private static Semester currentSemester;
    private ArrayList<String> courseList = null;
    //-------------------------------------------------------//
	public Semester(String year, String type, boolean status,String str,String str2) {
		
		this.year = year;
		this.type = type;
		this.status = status;
		this.str=str;
		this.str2=str2;
		courseList=new ArrayList<String>();
	}
	//-------------------------------------------------------//
    public void setCourseList(String ID)
    {
    	this.courseList.add(ID);
    }
    //-------------------------------------------------------//
    public ArrayList<String> getCourseList(){
		return courseList;
    }
    //-------------------------------------------------------//
	public String getYear() {
		return year;
	}
    //-------------------------------------------------------//
	public void setYear(String year) {
		this.year = year;
	}
    //-------------------------------------------------------//
	public String getType() {
		return type;
	}
    //-------------------------------------------------------//
	public void setType(String type) {
		this.type = type;
	}
    //-------------------------------------------------------//
	public boolean isStatus() {
		return status;
	}
    //-------------------------------------------------------//
	public void setStatus(boolean status) {
		this.status = status;
	}
    //-------------------------------------------------------//
	public static Semester getCurrentSemester() 
	{
		return currentSemester;
	}
	//-------------------------------------------------------//
	public static void setCurrentSemester(Semester currentSemester) 
	{
		Semester.currentSemester = currentSemester;
	}
	//-------------------------------------------------------//
	public static void setMyString(String str) 
	{
		Semester.str=str;
	}
	//-------------------------------------------------------//
	public static String GetMyString() 
	{
		return Semester.str;
	}
	//-------------------------------------------------------//
	public static void setMyString2(String str2) 
	{
		Semester.str2=str2;
	}
	//-------------------------------------------------------//
	public static String GetMyString2() 
	{
		return Semester.str2;
	}
	//-------------------------------------------------------//
	@Override
	public String toString() {
		return "Semester [year=" + year + ", type=" + type + ", status=" + status + ", courseList=" + courseList + "]";
	}
}

