package com.example.jorunal_bishe.exceptions;

import android.content.Context;


import com.example.jorunal_bishe.util.JFileKit;
import com.example.jorunal_bishe.util.JLogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogcatHelper {

	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd_HH_mm_ss", Locale.CHINA);
	protected static final SimpleDateFormat dayFormat = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.CHINA);
	private static LogcatHelper instance = null;
	private static String path;
	private LogDumper mLogDumper = null;
	private int mPId;

	private LogcatHelper(Context context) {
		init(context);
		mPId = android.os.Process.myPid();
	}

	public static LogcatHelper getInstance(Context context) {
		if (instance == null) {
			instance = new LogcatHelper(context);
		}
		return instance;
	}

	/**
	 * initial directory
	 *
	 * @param context
	 */
	private void init(Context context) {
		path = JFileKit.getDiskCacheDir(context) + "/logcat";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		double size = JFileKit.getFileOrFolderSize(path);
		if (size > 1024 * 20) {
			JFileKit.deleteAll(path);
		}
		JLogUtils.getInstance().i("size = " + size);
	}

	public void start() {
		if (mLogDumper == null)
			mLogDumper = new LogDumper(String.valueOf(mPId), path);
		mLogDumper.start();
	}

	public void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;
		private String saveDir;
		private String curFile;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			saveDir = dir;
			try {
				curFile = dateFormat.format(new Date()) + ".log";
				out = new FileOutputStream(new File(saveDir, curFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			/**
			 *
			 * log level：*:v , *:d , *:w , *:e , *:f , *:s
			 *
			 * According to the current mPID program E and W level of the log.
			 *
			 * */

			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat  | grep \"(" + mPID + ")\"";
			// cmds = "logcat -s way";
			cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";
		}

		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(
						logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((dateFormat.format(new Date()) + "  " + line + "\n")
								.getBytes());
						double size = JFileKit.getFileOrFolderSize(saveDir
								+ File.separator + curFile);
						if (size > 1024 * 2) {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								out = null;
							}
							File file = new File(saveDir);
							File files[] = file.listFiles();
							if (files.length > 10) {
								JFileKit.deleteAll(saveDir);
							}
							curFile = dateFormat.format(new Date()) + ".log";
							out = new FileOutputStream(new File(saveDir,
									curFile));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}
	}
}
