package com.example.jorunal_bishe.exceptions;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.util.Locale;

/**
 * The base exception handling system
 * 
 * @author
 * 
 */
public abstract class BaseExceptionHandler implements UncaughtExceptionHandler {

	protected static final DateFormat dateFormat = DateFormat
			.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.CHINA);

	/**
	 * Untrapped jump
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (handleException(ex)) {
			try {
				// If the processing, the program to exit continue running 3S,
				// which can save the error log
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// exit
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * Custom error handling, collect error information to send error reports
	 * and other operations in this complete, developers can according to their
	 * own circumstances exception handling logic custom
	 * 
	 * @param ex
	 * @return
	 */
	public abstract boolean handleException(Throwable ex);
}
