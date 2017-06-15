package Entity;

import java.util.Date;

public class SubTask {
	
	private String idTASK;
	private String idcourse;
	private String Sid;
	private float Grade;
	private int Mark;
	private String Comments;
	
	public SubTask(String idTASK, String idcourse,String SID, int mark,float grade,String comments) {
		super();
		this.idTASK = idTASK;
		this.idcourse = idcourse;
		Mark=mark;
		Grade = grade;
		Comments=comments;
		Sid=SID;
	}
	
	public String getIdTASK() {
		return idTASK;
	}
	public void setIdTASK(String idTASK) {
		this.idTASK = idTASK;
	}
	public String getIdcourse() {
		return idcourse;
	}
	public void setIdcourse(String idcourse) {
		this.idcourse = idcourse;
	}
	
	
	public String getcomments() {
		return Comments;
	}
	
	public float getgrade() {
		return Grade;
	}
	
	public void setMark(int m) {
	this.Mark=m	;
	}
	
	public int getMark() {
		return Mark;
		}
	public String getSIdTASK() {
		return Sid;
	}
	public void setSIdTASK(String s) {
		Sid=s;
	}

}
