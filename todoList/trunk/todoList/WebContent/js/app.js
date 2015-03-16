/**
 * 
 */

var app = angular.module("todo",['storageService']);

app.controller("TodoCtrl", ["$scope","getLocalStorage","$http","$log", function($scope, getLocalStorage, $http,$log) {
	
	  $http.get("./todos/all").success(function(data, status, headers, config) {
	  	$scope.todos = data;
	  }).error(function(data, status, headers, config) {
	  	alert("AJAX fail to get all todos");
	  });
	  
	  $scope.todos = getLocalStorage.getTodos();
	  $scope.selection = [];
	  $scope.markAll = false;
	  $scope.addTodo = function(todoText) {
	      if(event.keyCode == 13 && $scope.todoText){
	    	  var todoData = {
	    			  "content" : $scope.todoText,
	    			  "hasDone" : $scope.done
	    	  };
	          var response = $http.post('./todos', todoData);
	          
	          response.success(function(data, status, headers, config) {
					  $scope.todos = data;
					  $scope.todoText = '';
				  
			  });
			  
	          response.error(function(data, status, headers, config) {
				  alert("ToDo AJAX fail");
			  });
	      }
	  };
	  
	  $scope.isTodo = function(){
	      return $scope.todos.length > 0;  
	      getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  $scope.toggleEditMode = function(){
	      $(event.target).closest('li').toggleClass('editing');
	  };
	  
	  $scope.editOnEnter = function(todo, todoId){
	      if(event.keyCode == 13 && todo.text){
	          $scope.toggleEditMode();
	          var content = todo.text;
	          var response = $http.put("./todos/"+todoId, content);
	          response.success(function(data, status, headers, config) {
	          	$scope.todos = data; 
	          });
	          response.error(function(data, status, headers, config) {
	          	alert("AJAX 수정 에러");
	          });
	          
	          
	      };
	  };
	    
	  $scope.remaining = function() {
		  var count = 0;
			for(var todo in $scope.todos){
				if($scope.todos[todo].hasDone === true)count++;
			}
//			$http.get("./todos/count").success(function(data, status, headers, config) {
//				count = data;
//			}).error(function(data, status, headers, config) {
//				alert("에러에러에러");
//			});
		return count; 
		
	    getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  
	  $scope.hasDone = function() {
	      return ($scope.todos.length != $scope.remaining());
	      getLocalStorage.updateTodos($scope.todos);
	  };   
	  
	  //checkBox에 선택될때마다 selection 리스트에 todoId 저장
	  $scope.toggleSelection = function toggleSelection(todoId) {
		  var idx = $scope.selection.indexOf(todoId);
		  $scope.selection.push(todoId);
//		  $http.put("./todos/"+todoId+"/done").success(function(data, status, headers, config) {
//				//$scope.todos = data;
//			}).error(function(data, status, headers, config) {
//				alert("수정 AJAX 에러");
//			});
	};
	
	  //itemText. 1개면 item, 복수면 items 출력
	  $scope.itemText = function() {
	      return ($scope.todos.length - $scope.remaining > 1) ? "items" : "item";    
	      getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  
	  //전체선택
	  $scope.toggleMarkAll = function() {
	      angular.forEach($scope.todos, function(todo) {   //forEach (values, obj)
	    	$scope.selection.push(todo.todoId);
	        todo.hasDone =$scope.markAll;
	      });
	  };
	  
	  //선택된 todoList 삭제 
	  $scope.clear = function() {
		  console.log($scope.selection)
		  $http.post('./todos/delete/' + $scope.selection).success(function(data) {
		       $scope.todos = data;
		   }).error(function(data, status, headers, config) {
		   	  alert("리스트 삭제 Ajax 에러")
		   })
		  
	    getLocalStorage.updateTodos($scope.todos);
	  };
	  
	  //Todo한개 삭제 
	  $scope.deleteTodo = function(todoId) {
		$http.delete("./todos/"+todoId)
		.success(function(data, status, headers, config) {
			$scope.todos = data;
		})
		.error(function(data, status, headers, config) {
			alert("delete AJAX error");
		});
	};
		
	  //checkBox에 check될시 없데이트(사용X)
	  $scope.update  = function(todoId) {
		$http.put("./todos/"+todoId+"/done").success(function(data, status, headers, config) {
			$scope.todos = data;
		}).error(function(data, status, headers, config) {
			alert("수정 AJAX 에러");
		});
	};
	  
	  //header 
	  $scope.header = function(remaining) {
		var remaining =  +$scope.todos.length-$scope.remaining();
		//console.log('323');
		if(remaining== 0){
			return  "You have Nothing to do";
		}else if(remaining == 1){
			return  "You have 1 thing to do";
		}else {
			return "You have " +remaining +" things to do";
		}
	}; 

}]);

//localStorage에 저장
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
