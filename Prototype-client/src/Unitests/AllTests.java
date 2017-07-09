package Unitests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ MyNewControllerTest.class, SubmitTaskControllerTest.class, WatchTaskControllerTest.class })
public class AllTests

{
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(SubmitTaskControllerTest.class);
		suite.addTestSuite(WatchTaskControllerTest.class);
		suite.addTestSuite(MyNewControllerTest.class);
		
		//$JUnit-END$
		return suite;
	}

	
}
