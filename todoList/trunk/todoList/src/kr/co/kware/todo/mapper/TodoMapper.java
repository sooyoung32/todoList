package kr.co.kware.todo.mapper;

import java.util.List;

import kr.co.kware.todo.vo.Todo;

public interface TodoMapper  {
	
	public int insertTodo(Todo todo);
	public int updateTodo(Todo todo);
	public int updateHasDone(Todo todo);
	public int deleteTodo(int todoId);
	public List<Todo> selectTodoList(); 
	public Todo selectTodoItem(int todoId);
	public int countHasNotBeenDone();
	
	
}
