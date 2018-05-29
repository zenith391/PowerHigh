package org.lggl.utils.debug;

import java.util.Calendar;

public class DebugLogger {

	public static void logError(Object err) {

		DebugLogger.printDate();
		System.err.println("FATAL " + err);
	}

	public static void logInfo(Object inf) {
		DebugLogger.printDate();
		System.out.println("INFO " + inf);
	}

	public static void printDate() {
		System.out.print("[" + Calendar.getInstance().getTime() + "] ");
	}
}
