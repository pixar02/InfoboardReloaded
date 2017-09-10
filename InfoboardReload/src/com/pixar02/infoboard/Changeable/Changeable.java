package com.pixar02.infoboard.Changeable;

import java.util.ArrayList;

public class Changeable {
	private int counter = 0;
	private String message;
	private int row;
	private ArrayList<String> lines;

	public Changeable(int row, ArrayList<String> lines) {
		this.row = row;
		this.lines = new ArrayList<String>(lines);
		this.message = lines.get(0);
	}

	public int getRow() {
		return row;
	}

	public String getMessage() {
		if (message == null) {
			message = lines.get(0);
		}
		return message;
	}

	public void next() {
		if (lines.size() == counter) {
			counter = 0;
		} else {
			message = lines.get(counter);
			counter++;
		}
	}
}
