package com.agilesolutions.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agilesolutions.model.Task;
import com.agilesolutions.repository.ToDoRepository;

@RestController
@RequestMapping("api/v1/to-do-list")
public class ToDoListController {
	@Autowired
	private ToDoRepository toDoRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Task> findAll() {
		return toDoRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Task get(@PathVariable Long id) {
		return toDoRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Task create(@RequestBody Task task) {
		return toDoRepository.saveAndFlush(task);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Task update(@PathVariable Long id, @RequestBody Task newTask) {
		Task taskToUpdate = toDoRepository.findOne(id);
		BeanUtils.copyProperties(newTask, taskToUpdate); 
		return toDoRepository.saveAndFlush(taskToUpdate);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Task remove(@PathVariable Long id) {
		Task task = toDoRepository.findOne(id);
		toDoRepository.delete(task);
		return task;
	}
}
