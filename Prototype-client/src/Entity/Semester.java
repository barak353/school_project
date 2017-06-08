package Entity;

import java.sql.Date;
import java.util.ArrayList;

public class Semester {
	
	private String idSemester;
	
	private boolean status;
	
	private static Semester currentSemester;
	
    private ArrayList<Course> courseList = null;
    
    public void setCourseList(ArrayList<Course> courseList){
    	this.courseList = courseList;
    }
    
    public ArrayList<Course> getCourseList(){
		return courseList;
    }
	
	public Semester(String idSemester, boolean status) {
		super();
		this.idSemester = idSemester;
		this.status = status;
	}

	public String getidSemester() {
		return idSemester;
	}

	public void setidSemester(String idSemester) {
		this.idSemester = idSemester;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static Semester getCurrentSemester() {
		return currentSemester;
	}

	public static void setCurrentSemester(Semester currentSemester) {
		Semester.currentSemester = currentSemester;
	}
}
