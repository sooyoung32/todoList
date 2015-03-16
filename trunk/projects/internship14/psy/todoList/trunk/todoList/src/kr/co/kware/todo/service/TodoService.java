package kr.co.kware.todo.service;

import java.util.Date;
import java.util.List;

import kr.co.kware.todo.mapper.TodoMapper;
import kr.co.kware.todo.vo.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	@Autowired
	private TodoMapper todoMapper;
	
	public int writeTodo(String content){
		Todo todo = new Todo();
		todo.setDate(new Date());
		todo.setHasDone("false");
		todo.setContent(content);
		return todoMapper.insertTodo(todo);
	}
	
	public int modifyTodo(Todo todo){
		todo.setDate(new Date());
		System.err.println("todo¼­ºñ½º "+todo);
		return todoMapper.updateTodo(todo);
	}
	
	public int deleteTodo(int todoId){
		return todoMapper.deleteTodo(todoId);
	}
	public List<Todo> showTodoList(){
		return todoMapper.selectTodoList();
	}
	
	public Todo readTodo(int todoId){
		return todoMapper.selectTodoItem(todoId);
	}
	
	
	public int countHasNotBeenDone(){
		return todoMapper.countHasNotBeenDone();
	}
	
	public int modifyHasDone(Todo todo){
		todo.setDate(new Date());
		if(todo.getHasDone().equals("true")){
			todo.setHasDone("false");
		}else{
			todo.setHasDone("true");
		}
		return todoMapper.updateHasDone(todo);
	}
	
}
