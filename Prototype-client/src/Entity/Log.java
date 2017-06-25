package Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Log {

	
	public static PrintWriter writer;
	public static Date date;
	
	public Log(){
		try {
			File file = new File("log-client.txt");
			writer = new PrintWriter("log-client.txt");
			date = new Date();
			Log.print("1begining of log");
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void print(String log){
		writer.println(date+": "+log);
		writer.flush();
	}
	
}
