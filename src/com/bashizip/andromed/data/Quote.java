package com.bashizip.andromed.data;

import java.io.Serializable;



public class Quote implements Serializable{


	private long id;
	private String text;	
	private String author;

	public Quote() {

		
	}

	public Quote(String summary, String text, String author) {
		super();
		
		this.text = text;
		this.author = author;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
	
	
}
