package Unittests;
import Student.WatchTaskController;
import application.QueryController;

import junit.framework.TestCase;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WatchTaskControllerTest extends TestCase {

	
	
	private String IDsem ;
	private String courseID;
	private String fileName;

	@Before
	public void setUp() throws Exception {
		QueryController.connect("localhost",5555);
		 IDsem = new String("2017B");
		 courseID=new String("5565");
		 fileName=new String("גיליון ציונים חן חכמון.pdf");
 	
	}

	@After
	public void tearDown() throws Exception 
	{
		System.out.println("end");
		QueryController.connection.quit();
	}
	
	@Test
	public void testOpendFile() {
		WatchTaskController test =new WatchTaskController("controler test id"); 
		boolean expected = true;
		boolean result = test.OpenFile(IDsem, courseID,fileName);
		assertEquals(expected, result);
	
	}



}
