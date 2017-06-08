package Entity;

import java.sql.Date;
import java.util.ArrayList;

public class Semester {
	
	private String year;
	private String type;
	private boolean status;
	private static Semester currentSemester;
    private ArrayList<String> courseList = null;
    //-------------------------------------------------------//
	public Semester(String year, String type, boolean status) {
		this.year = year;
		this.type = type;
		this.status = status;
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
	public static Semester getCurrentSemester() {
		return currentSemester;
	}
	//-------------------------------------------------------//
	public static void setCurrentSemester(Semester currentSemester) {
		Semester.currentSemester = currentSemester;
	}
	//-------------------------------------------------------//
}
