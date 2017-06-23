package Entity;

import java.util.Date;

public class Task {
	private String TaskName;
	private String idcourse;
	private String SubDate;
	private String IDsem;
	private String fileExtN;
	
	public Task(String TaskName, String IDsem, String idcourse, String date, String fileExtN) {
		super();
		this.IDsem = IDsem;
		this.TaskName = TaskName;
		this.idcourse = idcourse;
		this.fileExtN = fileExtN;
		SubDate = date;
	}
	
	@Override
	public String toString() {
		return "Task [TaskName=" + TaskName + ", idcourse=" + idcourse + ", SubDate=" + SubDate + ", IDsem=" + IDsem
				+ ", fileExtN=" + fileExtN + "]";
	}

	public String getfileExtN() {
		return fileExtN;
	}
	
	public String getIDsem() {
		return IDsem;
	}
	
	public String getTaskName() {
		return TaskName;
	}

	public String getIdcourse() {
		return idcourse;
	}
	public void setIdcourse(String idcourse) {
		this.idcourse = idcourse;
	}
	public void setTaskName(String taskName) {
		TaskName = taskName;
	}
	public String getSubDate() {
		return SubDate;
	}
	public void setSubDate(String subDate) {
		SubDate = subDate;
	}
	public void setIDsem(String IDsem) {
		SubDate = IDsem;
	}
	public void setfileExtN(String fileExtN) {
		SubDate = fileExtN;
	}

}
