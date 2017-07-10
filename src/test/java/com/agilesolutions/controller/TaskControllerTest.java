package com.agilesolutions.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.agilesolutions.model.Task;
import com.agilesolutions.repository.TaskRepository;

public class TaskControllerTest {
	@InjectMocks
	TaskController taskCtrl;
	@Mock
	TaskRepository taskRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAll() {
		List<Task> mockTasks = new ArrayList<Task>();
		when(taskRepository.findAll()).thenReturn(mockTasks);
		
		List<Task> resultTasks = taskCtrl.findAll();
		verify(taskRepository).findAll();
		assertEquals(resultTasks.size(), 0);
	}
	
	@Test
	public void get() {
		Task mockTask = new Task();
		mockTask.setId(1l);
		when(taskRepository.findOne(1l)).thenReturn(mockTask);
		
		Task resultTask = taskCtrl.get(1l);
		verify(taskRepository).findOne(1l);
		assertEquals(1l, resultTask.getId().longValue());
	}
	
	@Test
	public void create() {
		String title = "Task";
		String text = "Task Description";
		
		when(taskRepository.saveAndFlush(any(Task.class))).thenAnswer(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();

                if (arguments != null && arguments.length > 0 && arguments[0] != null){
                    Task task = (Task) arguments[0];
                    task.setId(1l);
                    return task;
                }

                return null;

            }
        });

		Task newTask = new Task(title, text);
		Task resultTask = taskCtrl.create(newTask);
		verify(taskRepository).saveAndFlush(newTask);
		
		assertThat(resultTask, is(notNullValue()));
		assertEquals(1l, resultTask.getId().longValue());
		assertEquals(title, resultTask.getTitle());
		assertEquals(text, resultTask.getText());
	}
	
	@Test
	public void update() {
		List<Task> mockTasks = new ArrayList<Task>();
		Task mockTask = new Task();
		mockTask.setId(1l);
		
		when(taskRepository.findOne(1l)).thenReturn(mockTask);
		when(taskRepository.saveAndFlush(any(Task.class))).thenAnswer(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();

                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                    Task task = (Task) arguments[0];
                   
                    task.setTitle("Task");
                    task.setText("Task Description");
                    return task;
                }

                return null;
            }
        });
		
		Task resultTask = taskCtrl.update(1l, new Task("Title", "Text"));
		
		assertThat(resultTask, is(notNullValue()));
		assertEquals(1l, resultTask.getId().longValue());
		assertEquals("Task", resultTask.getTitle());
		assertEquals("Task Description", resultTask.getText());
	}

	@Test
	public void remove() {
		Task mockTask = new Task();

		mockTask.setId(1l);
		when(taskRepository.findOne(1l)).thenReturn(mockTask);
		when(taskRepository.saveAndFlush(any(Task.class))).thenAnswer(new Answer<Long>() {
            @Override
            public Long answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();

                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                    Task task = (Task) arguments[0];
                   
                    return task.getId();
                }

                return null;
            }
        });
		
		Long resultId = taskCtrl.remove(1l);
		
		assertThat(resultId, is(notNullValue()));
		assertEquals(1l, resultId.longValue());
	}
}
