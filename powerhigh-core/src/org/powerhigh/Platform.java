package org.powerhigh;

public enum Platform {
	
	LINUX,
	WINDOWS,
	OSX,
	ANDROID;
	
	public String getVersion() {
		return System.getProperty("os.version");
	}
	
	public static Platform getPlatform() {
		return PlatformManager.getPlatform();
	}

}
