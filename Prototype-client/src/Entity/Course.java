package Entity;

import java.util.ArrayList;

/**
 * This class will hold the Course information from the DB.
 * @return void
 */
public class Course {
	private String idcourses;

	private String courseName;
	
	private String teachingUnit;
	
	private String hours;
	
	public ArrayList<String> StudentList = null;
	
	public Course(String idcourses, String courseName, String teachingUnit, String hours)
	{
		super();
		this.idcourses = idcourses;
		this.courseName = courseName;
		this.teachingUnit = teachingUnit;
		this.hours = hours;
		StudentList=new ArrayList<String>();
	}


	/**
	 * get Course ID.
	 * @return void
	 * 
	 */
	public String getIdcourses() {
		return idcourses;
	}
	/**
	 * set course ID.
	 * @return void
	 * @param String
	 */
	public void setIdcourses(String idcourses) {
		this.idcourses = idcourses;
	}
	/**
	 * get course name.
	 * @return String
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * set course name.
	 * @return void
	 * @param String
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * get teaching unit.
	 * @return String
	 */
	public String getTeachingUnit() {
		return teachingUnit;
	}
	/**
	 * set teaching unit.
	 * @return void
	 */
	public void setTeachingUnit(String teachingUnit) {
		this.teachingUnit = teachingUnit;
	}
	/**
	 * get hours.
	 * @return String
	 * 
	 */
	public String getHours() {
		return hours;
	}
	/**
	 * set hours.
	 * @return void
	 * @param String
	 */
	public void setHours(String hours) {
		this.hours = hours;
	}
	
	
}
