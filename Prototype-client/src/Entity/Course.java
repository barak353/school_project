package Entity;

import java.util.ArrayList;

public class Course {
	private String idcourses;

	private String courseName;
	
	private String teachingUnit;
	
	private String hours;
	
	public static ArrayList<Course> courseList = null;
	
	public Course(String idcourses, String courseName, String teachingUnit, String hours) {
		super();
		this.idcourses = idcourses;
		this.courseName = courseName;
		this.teachingUnit = teachingUnit;
		this.hours = hours;
	}


	
	public String getIdcourses() {
		return idcourses;
	}

	public void setIdcourses(String idcourses) {
		this.idcourses = idcourses;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeachingUnit() {
		return teachingUnit;
	}

	public void setTeachingUnit(String teachingUnit) {
		this.teachingUnit = teachingUnit;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	
	
}
