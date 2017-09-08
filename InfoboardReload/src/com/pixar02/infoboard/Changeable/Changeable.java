package com.pixar02.infoboard.Changeable;

public class Changeable {
	private String message;
	private String origionalMessage;
	private int row;



	public Changeable(String message, int row) {
		this.row = row;
		this.origionalMessage = message;
	}

	public int getRow() {
		return row;
	}

	public void next() {
		// TODO Auto-generated method stub
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
