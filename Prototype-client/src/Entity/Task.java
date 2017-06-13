package Entity;

import java.util.Date;

public class Task {
	private String idTASK;
	private String idcourse;
	private String TaskName;
	private Date SubDate;
	
	public Task(String idTASK, String idcourse, String taskName, Date date) {
		super();
		this.idTASK = idTASK;
		this.idcourse = idcourse;
		TaskName = taskName;
		SubDate = date;
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
	public String getTaskName() {
		return TaskName;
	}
	public void setTaskName(String taskName) {
		TaskName = taskName;
	}
	public Date getSubDate() {
		return SubDate;
	}
	public void setSubDate(Date subDate) {
		SubDate = subDate;
	}

}
