/**
 * 
 */

var app = angular.module("todo",['storageService']);

app.controller("TodoCtrl", ["$scope","getLocalStorage", function($scope, getLocalStorage) {
	
	  $scope.todos = getLocalStorage.getTodos();
	  $scope.markAll = false;
	    
	  $scope.addTodo = function() {
	      if(event.keyCode == 13 && $scope.todoText){
	          $scope.todos.push({text : $scope.todoText, done : false});
	          getLocalStorage.updateTodos($scope.todos);
	          $scope.todoText = '';
	      }
	  };
	  
	  $scope.isTodo = function(){
	      return $scope.todos.length > 0;  
	      getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  $scope.toggleEditMode = function(){
	      $(event.target).closest('li').toggleClass('editing');
	  };
	  
	  $scope.editOnEnter = function(todo){
	      if(event.keyCode == 13 && todo.text){
	          $scope.toggleEditMode();
	      }
	  };
	    
	  $scope.remaining = function() {
	    var count = 0;
	    angular.forEach($scope.todos, function(todo) {
	      count += todo.done ? 0 : 1;						// todo.done = true ¸é 0, false¸é 1
	    });   
	    return count;
	    getLocalStorage.updateTodos($scope.todos);
	  };

	  $scope.hasDone = function() {
	      return ($scope.todos.length != $scope.remaining());
	      getLocalStorage.updateTodos($scope.todos);
	  };   
	    
	  $scope.itemText = function() {
	      return ($scope.todos.length - $scope.remaining() > 1) ? "items" : "item";     
	      getLocalStorage.updateTodos($scope.todos);
	  };
	      
	  $scope.toggleMarkAll = function() {
	      angular.forEach($scope.todos, function(todo) {   //forEach (values, obj)
	        todo.done =$scope.markAll;
	      });
	  };
	  
	  $scope.clear = function() {
	    var oldTodos = $scope.todos;
	    $scope.todos = [];
	    angular.forEach(oldTodos, function(todo) {
	      if (!todo.done) $scope.todos.push(todo);
	    });
	    getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  $scope.header = function(remaining) {
		var remaining =  $scope.remaining();
		if(remaining== 0){
			return  "Nothing to do"
		}else if(remaining == 1){
			return  "1 thing to do"
		}else {
			return "You have " + remaining +" things to do";
		}
		
	}; 

}]);


var storageService = angular.module('storageService',[]);
storageService.factory('getLocalStorage', function() {
	var todoList = {};
	
	return {
		list : todoList,
		
		updateTodos : function(todosArr) {
			if(window.localStorage && todosArr){
				localStorage.setItem("todos", angular.toJson(todosArr));
			}
			//update the cached version
			todoList = todosArr;
		},
		
		getTodos : function() {
			todoList = angular.fromJson(localStorage.getItem("todos"));
			return todoList? todoList : [];

			}
		};
	});
