package com.wm.notification.utils;

public class ExceptionScenario implements Cloneable {

	private String exceptionClazz;
	private String stackTrace;
	private String description = "No Descritpion";
	private String error = "No error";

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStackTrace(final String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getExceptionClazz() {
		return exceptionClazz;
	}

	public void setExceptionClazz(final String exceptionClazz) {
		this.exceptionClazz = exceptionClazz;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 1;

		hash = hash * prime
				+ (exceptionClazz == null ? 0 : exceptionClazz.hashCode());
		hash = hash * prime
				+ (stackTrace == null ? 0 : exceptionClazz.hashCode());
		return hash;

	}

	@Override
	public boolean equals(final Object obj) {

		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		ExceptionScenario newExpScenario = (ExceptionScenario) obj;

		return exceptionClazz.equals(newExpScenario.exceptionClazz)
				&& stackTrace.equals(newExpScenario.stackTrace);

	}

}
