package Unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entity.Semester;
import Secretary.MyNewController;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MyNewControllerTest extends TestCase {
	
	//---------------------------------------------------------------------------//
	private MyNewController ControllerCheck=new MyNewController("MyController");
	private Semester sem=new Semester("2017","c",true,"","");
	
	//---------------------------------------------------------------------------//
	@Before
	public void setUp() throws Exception 
	{
		sem.setCurrentSemester(sem);
		ControllerCheck.StatusJunit=1;
	}

	@After
	public void tearDown() throws Exception {
	}
	//-----------------------------------------------------------//
						/*Test for Class empty:*/
	@Test
	public void testClassEmpty() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse(null,"(5574) - Physics 2");
		System.out.println(""+Answer);
		assertEquals("Missing Class", Answer);
	}
	//-----------------------------------------------------------//
					/*Test for Course empty:*/
	@Test
	public void testCourseEmpty() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("A1",null);
		System.out.println(""+Answer);
		assertEquals("Missing Course", Answer);
	}
	//-----------------------------------------------------------//
					/*Test for Class empty and Course empty:*/
	/*@Test
	public void testCourseEmpty() 
	{
	String Answer;
	
	Answer=ControllerCheck.AssignClassToCourse("A1",null);
	System.out.println(""+Answer);
	assertEquals("Missing Course", Answer);
	}*/

}
