package Class;

import java.util.ArrayList;

import Secretary.Student;

public class Class {

	private String ClassName;
	private int ClassNum;
	private ArrayList<Student> StudentsArray;
	//--------------------------------------//
	public Class(String name, int num)
	{
		ClassName=name;
		ClassNum=num;
		StudentsArray=new ArrayList<Student>();
	}
	//--------------------------------------//
	public void SetName (String name)
	{
		this.ClassName=name;
	}
	//--------------------------------------//
	public void SetNum (int num)
	{
		this.ClassNum=num;
	}
	//--------------------------------------//
	public int GetNum ()
	{
		return this.ClassNum;
	}
	//--------------------------------------//
	public String GetName ()
	{
		return this.ClassName;
	}
	//--------------------------------------//
	public void SetStudent(Student S)
	{
		StudentsArray.add(S);
	}
	//--------------------------------------//
	public ArrayList<Student> GetStudent()
	{
		return StudentsArray;
	}
	//--------------------------------------//
}
