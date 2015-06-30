package com.wm.notification.cache;

public enum ApplicationExceptionCode {

	WAVEMAKER_STUDIO("Application execptions from wavemaker studio"),
	WAVEMAKER_PLATFORM("Application exceptions from wavemaker plaform"),
	WAVEMAKER_CLOUD("Application exceptions from wavemaker cloud"),
	WAVEMAKER_GENERIC("Generic wavemaker application exceptions");

	private final String description;

	private ApplicationExceptionCode(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
