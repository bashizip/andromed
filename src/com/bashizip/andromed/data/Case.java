package com.bashizip.andromed.data;



public class Case {

	
	private long id;
	private Investigation inv;
	private Patient pers;
	
	public Case() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Investigation getInv() {
		return inv;
	}

	public void setInv(Investigation inv) {
		this.inv = inv;
	}

	public Patient getPers() {
		return pers;
	}

	public void setPers(Patient pers) {
		this.pers = pers;
	}
	
	
	
}
