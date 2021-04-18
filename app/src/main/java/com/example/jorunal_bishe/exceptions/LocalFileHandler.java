package com.example.jorunal_bishe.exceptions;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;


import com.example.jorunal_bishe.util.JFileKit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Local exception class
 * 
 * @author
 * 
 */
public class LocalFileHandler extends BaseExceptionHandler {

	private Context context;

	public LocalFileHandler(Context context) {
		this.context = context;
	}

	/**
	 * Custom error handling, collect error information to send error reports
	 * and other operations in this complete, developers can according to their
	 * own circumstances exception handling logic custom
	 * 
	 * @param ex
	 * @return
	 */
	@Override
	public boolean handleException(final Throwable ex) {
		if (ex == null)
			return false;
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "Abnormal program", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
		ex.printStackTrace();
		//saveLog(ex);
		return true;
	}

	/**
	 * save log
	 * 
	 * @param ex
	 */
	private void saveLog(Throwable ex) {
		try {
			File errorFile = new File(JFileKit.getDiskCacheDir(context)
					+ "/log/crash.log");
			if (!errorFile.exists()) {
				errorFile.createNewFile();
			}
			OutputStream out = new FileOutputStream(errorFile, true);
			out.write(("\n\n------------error line"
					+ dateFormat.format(new Date()) + "-----------\n\n")
					.getBytes());
			PrintStream stream = new PrintStream(out);
			ex.printStackTrace(stream);
			stream.flush();
			out.flush();
			stream.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
