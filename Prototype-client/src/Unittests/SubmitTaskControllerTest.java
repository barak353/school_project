package Unittests;
import Student.SubmitTaskController;
import application.QueryController;
import junit.framework.TestCase;
import static org.junit.Assert.*;

import java.io.File;

import javax.swing.JFileChooser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubmitTaskControllerTest extends TestCase {
	
	private String IDNcourse;
	private String TaskName ;
	private String IDstudent;
	private String IDsem ;
	private String IDsemQ;
	private String courseID;
	private String studentID;
	private File file;
	

	@Before
	public void setUp() throws Exception {
		QueryController.connect("localhost",5555);
		 IDNcourse = new String("5565");
		 TaskName = new String("sub1");
		 IDstudent= new String("8");
		 IDsem = new String("2017:B");
		 IDsemQ= new String("2017:B");
		 courseID=new String("5565");
		 studentID=new String("8");
		 
		 
		
		 
	}
	
	@After
	public void tearDown() throws Exception 
	{
		System.out.println("end");
		QueryController.connection.quit();
	}

	
	@Test
	public void testUploadFile() {
		SubmitTaskController test =new SubmitTaskController("controler test id"); 
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		boolean expected = true;
		test.isNotTest=false;
		boolean result = test.uploadFile(IDNcourse, TaskName, IDstudent, IDsem, IDsemQ, courseID, studentID,file);
		assertEquals(expected, result);
	
	}


}


