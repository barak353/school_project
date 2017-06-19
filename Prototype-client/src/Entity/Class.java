package Entity;

import java.util.ArrayList;

public class Class {

	private String ClassName;
	private ArrayList<String> StudentsArray;
	//--------------------------------------//
	public Class(String name)
	{
		ClassName=name;
		StudentsArray=new ArrayList<String>();
	}
	//--------------------------------------//
	public void SetName (String name)
	{
		this.ClassName=name;
	}
	//--------------------------------------//
	public String GetName ()
	{
		return this.ClassName;
	}
	//--------------------------------------//
	public void SetStudent(String S)
	{
		StudentsArray.add(S);
	}
	//--------------------------------------//
	public ArrayList<String> GetStudent()
	{
		return StudentsArray;
	}
	//--------------------------------------//
}
