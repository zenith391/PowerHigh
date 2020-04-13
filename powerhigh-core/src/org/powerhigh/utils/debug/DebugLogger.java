package org.powerhigh.utils.debug;

import java.util.Calendar;

public class DebugLogger {

	private static boolean debugMode;
	
	/**
	 * Only logs if the debug mode is enabled.
	 * @param deb
	 * @return debug mode state
	 * @see {@link DebugLogger#isDebugModeEnabled()}
	 * @see {@link DebugLogger#setDebugModeEnabled(boolean)}
	 */
	public static boolean debug(Object debug) {
		if (debugMode) {
			DebugLogger.printDate();
			System.out.println("DEBUG " + debug);
		}
		return debugMode;
	}

	public static void logError(Object err) {
		DebugLogger.printDate();
		System.err.println("FATAL " + err);
	}
	
	public static void logWarn(Object warn) {
		DebugLogger.printDate();
		System.err.println("WARN " + warn);
	}

	public static boolean isDebugModeEnabled() {
		return debugMode;
	}
	
	/**
	 * Set whether or not {@link DebugLogger#debug(Object)} should actually log.
	 * Debug mode is <code>false</code> by default.
	 * @param debugMode boolean
	 * @see {@link DebugLogger#debug(Object)}
	 * @see {@link DebugLogger#isDebugModeEnabled()}
	 */
	public static void setDebugModeEnabled(boolean debugMode) {
		DebugLogger.debugMode = debugMode;
	}

	public static void logInfo(Object inf) {
		DebugLogger.printDate();
		System.out.println("INFO " + inf);
	}

	public static void printDate() {
		System.out.print("[" + Calendar.getInstance().getTime() + "] ");
	}
}
