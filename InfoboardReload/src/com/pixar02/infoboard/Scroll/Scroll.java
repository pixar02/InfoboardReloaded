package com.pixar02.infoboard.Scroll;

import org.bukkit.ChatColor;

import com.pixar02.infoboard.Utils.Messages;

public class Scroll {

	private String message;
	private String origionalMessage;

	private ChatColor color = ChatColor.RESET;

	private int width;
	private int position = 0;
	private int pause = 0;
	private int row;

	/**
	 * Create a new scroller
	 *
	 * @param message
	 * @param row
	 * @param width
	 */
	public Scroll(String message, int row, int width) {
		this.row = row;
		this.width = width;
		this.origionalMessage = message;
		StringBuilder builder = new StringBuilder(message);
		while (builder.length() <= (width * 2)) {
			builder.append("          ").append(message);
		}
		String string = builder.toString();

		string = Messages.getColored(string);

		this.message = string;
	}

	/**
	 * Get the scrolled message
	 *
	 * @return message
	 */
	public String getMessage() {

		String message = this.message.substring(position, Math.min(this.message.length(), (width - 2) + position));
		char COLORCHAR = '&';
		if (message.charAt(0) == COLORCHAR) {
			color = ChatColor.getByChar(message.charAt(1));
		} else {
			message = message.substring(1, message.length());
			message = "" + color + message;
		}

		if (message.charAt(message.length() - 1) == COLORCHAR) {
			message = message.substring(0, message.length() - 2);
			message = message + " ";
		}
		return message;

	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Move position up one unless it's being paused for 3 counts first
	 */
	public void next() {

		if ((position == 0) && (pause != 3)) {
			pause++;
		} else {
			position++;
			pause = 0;

			if (position == (origionalMessage.length() + 10)) {
				position = 0;
			}
		}

	}

}
