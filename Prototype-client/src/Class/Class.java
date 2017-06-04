package Class;

import java.util.ArrayList;

import student.Student;

public class Class {

	private String ClassName;
	private String ClassNum;
	private ArrayList<String> StudentsArray;
	//--------------------------------------//
	public Class(String name, String num)
	{
		ClassName=name;
		ClassNum=num;
		StudentsArray=new ArrayList<String>();
	}
	//--------------------------------------//
	public void SetName (String name)
	{
		this.ClassName=name;
	}
	//--------------------------------------//
	public void SetNum (String num)
	{
		this.ClassNum=num;
	}
	//--------------------------------------//
	public String GetNum ()
	{
		return this.ClassNum;
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
