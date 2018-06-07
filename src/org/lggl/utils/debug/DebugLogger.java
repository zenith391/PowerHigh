package org.lggl.utils.debug;

import java.util.Calendar;

public class DebugLogger {

	private static boolean debugMode;

	public static boolean debug(Object deb) {
		if (debugMode) {
			DebugLogger.printDate();
			System.out.println("DEBUG " + deb);
		}

		return debugMode;
	}

	public static void logError(Object err) {

		DebugLogger.printDate();
		System.err.println("FATAL " + err);
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static void setDebugMode(boolean debugMode) {
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
