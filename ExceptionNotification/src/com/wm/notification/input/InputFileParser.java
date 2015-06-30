package com.wm.notification.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wm.notification.cache.ApplicationExceptionCache;

public class InputFileParser extends AbstractInputParser {

	private final File fileName;
	public final ApplicationExceptionCache exCache = ApplicationExceptionCache
			.getExceptionCache();

	public InputFileParser(final File fileName) {

		this.fileName = fileName;
	}

	@Override
	public void read() {
		try {
			List<File> logFiles = new ArrayList<File>();

			if (!fileName.isFile()) {

				File[] fileNames = new File(fileName.getPath()).listFiles();

				for (File files : fileNames) {
					if (files.isFile()) {
						logFiles.add(files);
					}
				}

			} else {
				logFiles.add(fileName);
			}

			for (File logFile : logFiles) {

				BufferedReader br = new BufferedReader(new FileReader(logFile));
				parse(br);

			}
			exCache.printExceptionCounter();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
