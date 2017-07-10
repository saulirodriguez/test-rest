package com.agilesolutions.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import com.agilesolutions.model.Task;
import com.agilesolutions.repository.TaskRepository;

@RestController
@RequestMapping("api/v1/to-do-list/task")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Task>> findAll(@RequestParam Map<String,String> allRequestParams) {
		String title = allRequestParams.get("title");
		String text = allRequestParams.get("text");
		try {
			List<Task> tasks = taskRepository.findAll();

			if(title != null && !title.equals("")) {
				tasks = tasks.stream().filter(t -> t.getTitle().matches(("(?i:.*" + title + ".*)"))).collect(Collectors.toList());
			}
			
			if(text != null && !text.equals("")) {
				tasks = tasks.stream().filter(t -> t.getText().matches(("(?i:.*" + text + ".*)"))).collect(Collectors.toList());
			}
			return ResponseEntity.ok(tasks);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Task> get(@PathVariable Long id) {
		try {
			Task task = taskRepository.findOne(id);
			if(task == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(task);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Task> create(@RequestBody Task task) {
		try {
			Boolean empty = task.getText().equals("") && task.getTitle().equals("");
			Boolean hasId = task.getId() != null;

			if(empty || hasId) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.saveAndFlush(task));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
		}
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task newTask) {
		try {
			Boolean empty = newTask.getText().equals("") && newTask.getTitle().equals("");
			Boolean hasId = newTask.getId() != null;
			
			if(empty || hasId) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			Task taskToUpdate = taskRepository.findOne(id);
			if(taskToUpdate == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			BeanUtils.copyProperties(newTask, taskToUpdate);
			taskToUpdate.setId(id);
			return ResponseEntity.ok(taskRepository.saveAndFlush(taskToUpdate));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		try {
			Task task = taskRepository.findOne(id);
			if(task == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			taskRepository.delete(task);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
		}
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	void handleBadRequestsErrorParam(HttpServletResponse response) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	void handleBadRequestsError(HttpServletResponse response) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}
}
