package Unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.ClientGui;
import application.QueryController;
import Entity.Semester;
import Secretary.MyNewController;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MyNewControllerTest extends TestCase {
	
	//---------------------------------------------------------------------------//
	private static MyNewController ControllerCheck=new MyNewController("MyController");
	private static Semester sem=new Semester("2017","c",true,"","");
	private static boolean isFirstTime = false;
	
	//---------------------------------------------------------------------------//
	@Before
	public void setUp() throws Exception 
	{
		System.out.println("start");
		sem.setCurrentSemester(sem);
		ControllerCheck.StatusJunit=1;
		//if(isFirstTime  == true){
		//	System.out.println("test2");
			QueryController.connect("localhost",5555);
		//}
		//isFirstTime = true;
	}

	@After
	public void tearDown() throws Exception 
	{
		System.out.println("end");
		QueryController.connection.quit();
	}
	//-----------------------------------------------------------//
						/*Test for Class empty:*/
	@Test
	public void testClassEmpty() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse(null,"(5574) - Physics 2");
		//System.out.println("end");
		//QueryController.connection.quit();
		assertEquals("Missing Class", Answer);
	}
	//-----------------------------------------------------------//
					/*Test for Course empty:*/
	@Test
	public void testCourseEmpty() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("A1",null);
		//System.out.println("end");
		//QueryController.connection.quit();
		assertEquals("Missing Course", Answer);
		
	}
	//-----------------------------------------------------------//
			/*Test for Class empty and Course empty:*/
	@Test
	public void testCourseAndClassEmpty() 
	{
		String Answer;
		
		
		Answer=ControllerCheck.AssignClassToCourse(null,null);
		//System.out.println("end");
		//QueryController.connection.quit();
		assertEquals("Missing Class and Course", Answer);
		
	}
	//-----------------------------------------------------------//
		     /*Test for Class already assigned to the course:*/
	@Test
	public void testClassAlreadyAssigned() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("A1","(5573) - Physics 1");
		//System.out.println("end");
		//QueryController.connection.quit();
		assertEquals("Class Already Assigned", Answer);
		
	}
	//-----------------------------------------------------------//
					/*Course Without pre course:*/
	@Test
	public void testCourseWithoutPreCourses() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("A1","(5577) - Tehen Logi");
		
		assertEquals("Success", Answer);
		
		
	}
	
}
