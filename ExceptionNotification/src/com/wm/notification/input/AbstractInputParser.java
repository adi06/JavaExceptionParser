package com.wm.notification.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wm.notification.cache.ApplicationExceptionCache;
import com.wm.notification.utils.ExceptionScenario;

public abstract class AbstractInputParser {

	public final ApplicationExceptionCache exCache = ApplicationExceptionCache
			.getExceptionCache();

	public final String regex = "^[a-z].*Exception";
	public final String date_regex1 = "^[0-9]{2}\\s[a-zA-Z]{3}\\s[0-9]{4}";
	public final String date_regex2 = "^[0-9]{2}:[0-9]{2}:[0-9]{2}";

	public final Pattern pattern = Pattern.compile(regex);
	public final Pattern pattern1 = Pattern.compile(date_regex1);
	public final Pattern pattern2 = Pattern.compile(date_regex2);

	public final String[] PATTERN_KEYWORDS = { "at", "Caused by:", "ERROR",
			"WARN" };

	public ExceptionScenario exceptionScenario;

	public abstract void read();

	public void parse(final BufferedReader br) {

		try {
			parseAndUpdateExceptionCache(br);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void parseAndUpdateExceptionCache(final BufferedReader br)
			throws NullPointerException, IOException {

		String line = br.readLine();

		while (line != null) {

			Matcher match1 = pattern1.matcher(line);
			Matcher match2 = pattern2.matcher(line);

			String innerLine = null;

			if (match1.find() || match2.find()) {

				String next = br.readLine();
				if (next != null) {
					Matcher match = pattern.matcher(next);

					if (match.find()) {

						exceptionScenario = new ExceptionScenario();

						exceptionScenario.setError(line);

						String[] exc = next.split(":");

						if (exc == null || exc[0] == null)
							throw new NullPointerException(
									"splitting exception failed");

						exceptionScenario.setExceptionClazz(exc[0]);

						exceptionScenario.setDescription(next);

						StringBuffer sb = new StringBuffer();

						innerLine = copyStackTrace(br, sb);

						exceptionScenario.setStackTrace(sb.toString());

						exCache.addExceptionToMap(
								exceptionScenario.getExceptionClazz(),
								exceptionScenario);

					}
				}
			}

			line = innerLine != null ? innerLine : br.readLine();
		}
	}

	public void setExceptionScenario(final String[] exc, final StringBuffer sb,
			final String error) {

		exceptionScenario = new ExceptionScenario();

		exceptionScenario.setExceptionClazz(exc[0]);
		if (exc.length > 1) {
			exceptionScenario.setDescription(exc[1]);
		}

		exceptionScenario.setStackTrace(sb.toString());
		if (error != null) {
			exceptionScenario.setError(error);
		}
	}

	public String copyStackTrace(final BufferedReader br, final StringBuffer sb)
			throws IOException {

		String innerLine = br.readLine();

		while (innerLine != null

		/*
		 * && (innerLine.trim().startsWith(PATTERN_KEYWORDS[0]) || innerLine
		 * .startsWith(PATTERN_KEYWORDS[1]))
		 */) {

			Matcher match1 = pattern1.matcher(innerLine);
			Matcher match2 = pattern2.matcher(innerLine);
			if (!(match1.find() || match2.find())) {

				sb.append(innerLine).append("\n");
				innerLine = br.readLine();
			} else {
				break;
			}
		}
		return innerLine;

	}

}
