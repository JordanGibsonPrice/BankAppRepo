package com.revature.services;

import org.apache.log4j.Logger;

public class LoggingUtil {
	private static Logger Log = Logger.getRootLogger();
//from most to least verbose
	public static void logFatal(String s) {
		Log.fatal(s);
	}
	public static void logError(String s) {
		Log.error(s);
	}
	public static void logWarn(String s) {
		Log.warn(s);
	}
	public static void logInfo(String s) {
		Log.info(s);
	}
	public static void logDebug(String s) {
		Log.debug(s);
	}
	public static void logTrace(String s) { 
		Log.trace(s);
	}
	
	public static void main(String args[]) {
		LoggingUtil.logDebug("debug");
		LoggingUtil.logError("Error");
		LoggingUtil.logInfo("Info");
		LoggingUtil.logTrace("Trace");
		LoggingUtil.logWarn("Warn");
		LoggingUtil.logFatal("fatal");
	} 
}
