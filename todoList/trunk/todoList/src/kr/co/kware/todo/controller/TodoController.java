package kr.co.kware.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.kware.todo.service.TodoService;
import kr.co.kware.todo.vo.Todo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	private Logger logger = Logger.getLogger("TodoController");
	
	
	@RequestMapping(value ="/todos", method=RequestMethod.POST)
	public 	@ResponseBody List<Todo> writeTodo(@RequestBody Todo todo){
		todoService.writeTodo(todo.getContent());
		List<Todo> result = todoService.showTodoList();
		return result;
	}
	
	@RequestMapping(value ="/todos/{todoId}", method=RequestMethod.PUT)
	public @ResponseBody List<Todo> modifyTodo(@PathVariable int todoId, @RequestBody String content) {
		Todo todo = todoService.readTodo(todoId);
		todo.setContent(content);
		todoService.modifyTodo(todo);
		System.err.println("컨트롤러 : "+todo);
		List<Todo> result = todoService.showTodoList();
		return result;
	}
	
	@RequestMapping(value ="/todo/delete", method=RequestMethod.POST)
	@ResponseBody
	public List<Todo> deleteTodo(@RequestBody List<Todo> todos){
		
		logger.debug(todos.get(1).toString());
		for(int i =0; i<todos.size(); i++){
//			todoService.deleteTodo(todos.get(i));
		}
		List<Todo> result = todoService.showTodoList();
		
		
		return result;
	}
	
	@RequestMapping(value ="/todos/{todoId}", method=RequestMethod.DELETE)
	public	@ResponseBody List<Todo> deleteTodo(@PathVariable int todoId){
		
		System.err.println(todoId);
		todoService.deleteTodo(todoId);
		List<Todo> result = todoService.showTodoList();
		
		
		return result;
	}
	
	
	@RequestMapping(value ="/todos/{todoId}/done", method=RequestMethod.PUT)
	public @ResponseBody List<Todo> updateDone(@PathVariable int todoId){
		Todo todo = todoService.readTodo(todoId);
		todoService.modifyHasDone(todo);
		
		List<Todo> result = todoService.showTodoList();
		return result;
	}
	
	
	@RequestMapping(value ="/todos")
	public ModelAndView showTodoList(){
		return new ModelAndView("todo_list");
	}
	
	@RequestMapping(value ="/todos/all", method=RequestMethod.GET)
	public @ResponseBody List<Todo> showTodoList2(){
		List<Todo> todoList = todoService.showTodoList();
		return todoList;
		
	}
	
	@RequestMapping(value ="/todos/count", method=RequestMethod.GET)
	public @ResponseBody int countHasNotBeenDone(){
		
		int remaining = todoService.countHasNotBeenDone();
		System.err.println("remaining = "+remaining);
		return remaining;
	}
	
	@RequestMapping(value="/todos/delete/{todoIds}", method=RequestMethod.POST, headers="Accept=application/json")
	public  @ResponseBody List<Todo> archiveAllTodos(@PathVariable int[] todoIds){
		for(int i= 0 ; i < todoIds.length; i++){
			todoService.deleteTodo(todoIds[i]);
			System.err.println(todoIds[i]);
		}
		List<Todo> todoList = todoService.showTodoList();
		return todoList;
	}
	
	
}
