package com.wm.notification.utils;

import java.io.File;

import com.wm.notification.input.AbstractInputParser;
import com.wm.notification.input.InputFileParser;

public class LogsParser {

	public static void main(final String[] args) {

		File file = new File(args[0]);

		final AbstractInputParser ifp = new InputFileParser(file);

		ifp.read();

	}
}
