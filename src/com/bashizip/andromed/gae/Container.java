package com.bashizip.andromed.gae;

import java.util.ArrayList;
import java.util.List;

import com.bashizip.andromed.data.User;

public class Container<T> {
	public List list;

	public Container() {
		list = new ArrayList();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List T) {
		this.list = T;
	}

	public Container(List T) {
		this.list = list;
	}



}
