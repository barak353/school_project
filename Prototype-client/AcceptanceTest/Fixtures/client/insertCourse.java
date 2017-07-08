/**
 * Generated by FITpro Fixture Generator
 */
package Fixtures.client;

import java.io.IOException;

import SystemManager.SystemManagerAddCourseController;
import application.QueryController;
import fit.ActionFixture;

/*
 * TODO: modify class to match the FIT test - edit and add methods as required.
 */
public class insertCourse extends ActionFixture {
	SystemManagerAddCourseController controllerTest;
	private String TeachingUnit;
	private String nameCourse;
	private String Hours;
	public static boolean isNotTest = false;
    
	public String insertCourse() {
		return String.valueOf(controllerTest.insertCourse(TeachingUnit,nameCourse,Hours));
	}
  
    
	public void setHours(String setHours) {
		this.Hours = setHours;
	}
  
    
	public void setTeachingUnit(String setTeachingUnit) {
		this.TeachingUnit = setTeachingUnit;
	}
  
    
	public void setnameCourse(String setnameCourse) {
		this.nameCourse = setnameCourse;
	}
  
    
	public void startController() {
		try {
			QueryController.connect("localhost", 5555);
		} catch (IOException e) {
			e.printStackTrace();
		}
		controllerTest = new SystemManagerAddCourseController("ControllerTestID");
		controllerTest.isNotTest = false;
	}
  
	public void endController() {
			QueryController.connection.quit();
	}
	
}