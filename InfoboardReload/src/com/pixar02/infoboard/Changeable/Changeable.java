package com.pixar02.infoboard.Changeable;

import com.pixar02.infoboard.Utils.Messages;

public class Changeable {
	private String message;
	private String origionalMessage;
	private int row;

	public Changeable(String message, int row) {
		this.row = row;
		this.origionalMessage = message;

		message = Messages.getColored(message);
		this.message = message;
	}

	public int getRow() {
		return row;
	}

	public String getMessage() {

		String message = this.message;
		return message;
	}

	public void next() {
		// TODO Auto-generated method stub
	}
}
