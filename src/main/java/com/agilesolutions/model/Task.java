package com.agilesolutions.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private
	Long id;
	String title;
	String text;
	LocalDateTime createdAt;
	
	public Task() {
		this.title = "";
		this.text = "";
		this.setCreatedAt(LocalDateTime.now());
	}
	
	public Task(String title, String text) {
		this.setTitle(title);
		this.setText(text);
		this.setCreatedAt(LocalDateTime.now());
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
