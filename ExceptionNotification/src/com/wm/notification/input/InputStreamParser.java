package com.wm.notification.input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamParser extends AbstractInputParser {

	private final InputStream inputStream;

	public InputStreamParser(final InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void read() {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		parse(br);
	}

}
