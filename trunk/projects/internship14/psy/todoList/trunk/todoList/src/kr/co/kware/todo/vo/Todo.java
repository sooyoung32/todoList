package kr.co.kware.todo.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

public class Todo {

	private int todoId;
	private String content;
	private String hasDone;
	private Date date;
	
	
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHasDone() {
		return hasDone;
	}
	public void setHasDone(String hasDone) {
		this.hasDone = hasDone;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", content=" + content + ", hasDone=" + hasDone + ", date=" + date + "]";
	}
	
	
}
