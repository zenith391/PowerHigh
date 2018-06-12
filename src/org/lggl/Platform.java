package org.lggl;

public enum Platform {
	
	LINUX,
	WINDOWS,
	OSX,
	ANDROID;
	
	public String getVersion() {
		return System.getProperty("os.version");
	}

}
