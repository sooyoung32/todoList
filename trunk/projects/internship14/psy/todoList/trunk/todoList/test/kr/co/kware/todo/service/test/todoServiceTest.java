package kr.co.kware.todo.service.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import kr.co.kware.todo.mapper.TodoMapper;
import kr.co.kware.todo.service.TodoService;
import kr.co.kware.todo.vo.Todo;

import org.junit.Test;

public class todoServiceTest {
	
	private TodoService todoService = new TodoService();
	
	@Test
	public void testWriteTodo() {
		Todo todo = new Todo();
		todo.setTodoId(2);
		todo.setContent("����");
		todo.setHasDone("N");
		todo.setDate(new Date());
		assertEquals(1, todoService.writeTodo(todo.getContent()));
	}

//	@Test
//	public void testModifyTodo() {
//		Todo todo = new Todo();
//		todo.setTodoId(1);
//		assertEquals(1, todoService.modifyTodo("����", 1));
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testDeleteTodo() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testShowTodoList() {
//		fail("Not yet implemented");
//	}

}
