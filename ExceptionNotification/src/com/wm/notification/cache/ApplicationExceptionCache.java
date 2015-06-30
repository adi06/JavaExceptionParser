package com.wm.notification.cache;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.wm.notification.utils.ExceptionScenario;

public final class ApplicationExceptionCache {

	private final Map<String, Set<ExceptionScenario>> exceptionMap = new ConcurrentHashMap<String, Set<ExceptionScenario>>();

	private final Map<String, Integer> exceptiomMapCounter = new HashMap<String, Integer>();

	private static ApplicationExceptionCache exceptionCache;

	private ApplicationExceptionCode exceptionApplicationCode;

	private ApplicationExceptionCache() {

	}

	public synchronized static ApplicationExceptionCache getExceptionCache() {
		if (exceptionCache == null) {
			exceptionCache = new ApplicationExceptionCache();
		}
		return exceptionCache;

	}

	public void addExceptionToMap(final String expClazz,
			final ExceptionScenario exceptionScenrio) {

		final String key = expClazz;
		if (!isExceptionAvailable(key)) {
			final Set<ExceptionScenario> stackTraceSet = new HashSet<ExceptionScenario>();
			exceptionMap.put(key, stackTraceSet);
			exceptiomMapCounter.put(key, 1);
		}
		int count = exceptiomMapCounter.get(key);
		exceptiomMapCounter.put(key, ++count);
		Set<ExceptionScenario> uniqueStackTraceSet = exceptionMap.get(key);
		uniqueStackTraceSet.add(exceptionScenrio);
	}

	public boolean isExceptionAvailable(final String key) {
		return exceptionMap.containsKey(key);
	}

	public Map<String, Set<ExceptionScenario>> getExceptionMap() {
		return exceptionMap;
	}

	public void printExceptionCounter() throws IOException {
		final BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"Exceptions.txt")));
		bw.write("######################################################################\n");
		bw.write("Statistics of Unique Stack trace count with total exceptions logged\n ");
		bw.write("######################################################################\n");

		for (String key : exceptionMap.keySet()) {
			System.out.println(key + "-->" + exceptionMap.get(key).size()
					+ " in " + exceptiomMapCounter.get(key));
			bw.write(key + "-->" + exceptionMap.get(key).size() + "\n");

			for (ExceptionScenario obj : exceptionMap.get(key)) {
				bw.write(obj.getError() + "\n");
				bw.write(obj.getDescription() + "\n");
				bw.write(obj.getStackTrace() + "\n");
				bw.write("##############################\n");
				bw.flush();

			}

		}
		bw.flush();
		bw.close();

	}

	public ApplicationExceptionCode getExceptionApplicationCode() {
		return exceptionApplicationCode;
	}

	public void setExceptionApplicationCode(
			final ApplicationExceptionCode exceptionApplicationCode) {
		this.exceptionApplicationCode = exceptionApplicationCode;
	}
}
