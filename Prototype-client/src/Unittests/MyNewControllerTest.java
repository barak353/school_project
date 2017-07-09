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
		
		Answer=ControllerCheck.AssignClassToCourse("A1","(5572) - Misdhif");
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
	//----------------------------------------------------------------//
	/*Course Without pre course and all the students already assigned:*/
	@Test
	public void testCourseWithoutPreCoursesAllTheStudentsAssigned() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("A2","(5577) - Tehen Logi");
		assertEquals("ALL",Answer);
	}
	//----------------------------------------------------------------//
	      /*Course Without pre course and half students assigned:*/
	@Test
	public void testCourseWithoutPreCoursesHalfStudentsAssigned() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("B1","(5577) - Tehen Logi");
		assertEquals(""+"\nShir",Answer);
	}
	//----------------------------------------------------------------//
            /*Course with pre courses class already assigned:*/
	@Test
	public void testCourseWithPreCourseClassArlreadyAssigned() 
	{
		String Answer;
		
		Answer=ControllerCheck.AssignClassToCourse("B2","(5569) - English 2");
		assertEquals("Class Already Assigned",Answer);
	}
	//----------------------------------------------------------------//
	    /*Course with pre courses all the students fullfil pre courses*/
	@Test
	public void testCourseWithPreCourseAllStudentsOK() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("B1","(5570) - English 3");
		assertEquals("Success",Answer);
	}
	//----------------------------------------------------------------//
		/*Course with pre courses all the students fullfil pre courses*/
	@Test
	public void testCourseWithPreCourseAllNotFullfil() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("B1","(5567) - Matam");
		assertEquals("ALL",Answer);
	}
	//----------------------------------------------------------------//
	/*Course with pre courses half class is already assigned and the other half not:*/
	@Test
	public void testCourseWithPreCourseStudentAlreadyAssigned() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("B2","(5565) - Algebra 2");
		assertEquals(""+"\nRoi",Answer);
	}
	//----------------------------------------------------------------//
	/*Course with pre courses half class is already assigned and the other half not fullfil
	  pre courses*/
	@Test
	public void testCourseWithPreCourseHalfNotFulfill() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("A1","(5569) - English 2");
		assertEquals("ALL",Answer);
	}
	//----------------------------------------------------------------//
	/*Course with pre courses half class fullfil pre courses (grades<55)*/
	@Test
	public void testCourseWithPreCourseHalffullfil() 
	{
		String Answer;
	
		Answer=ControllerCheck.AssignClassToCourse("A1","(5566) - Hedva 2");
		assertEquals(""+"\nnofarT+\nSnir+\n+Michael",Answer);
	}
}
