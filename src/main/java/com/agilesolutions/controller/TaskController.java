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
import com.agilesolutions.repository.TaskRepository;

@RestController
@RequestMapping("api/v1/to-do-list/task")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Task get(@PathVariable Long id) {
		return taskRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Task create(@RequestBody Task task) {
		return taskRepository.saveAndFlush(task);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Task update(@PathVariable Long id, @RequestBody Task newTask) {
		Task taskToUpdate = taskRepository.findOne(id);
		BeanUtils.copyProperties(taskToUpdate, newTask);

		return taskRepository.saveAndFlush(taskToUpdate);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Long remove(@PathVariable Long id) {
		Task task = taskRepository.findOne(id);
		taskRepository.delete(task);
		return id;
	}
}
