package com.pixar02.infoboard.Changeable;

import com.pixar02.infoboard.Utils.Messages;

public class Changeable {
	private int counter;
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
		// TODO check if the Amount of lines equals the counter
		// if(/*.length ==*/ counter){
			 counter = 0;
		// } else {
			 counter++;
		// }
	}
}
