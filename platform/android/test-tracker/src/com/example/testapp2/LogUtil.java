package com.example.testapp2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class LogUtil {
	private static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	
	public static final void log(String msg) {
		File file = new File(path, "trackerlog.txt");
		if (!file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file);//context.openFileOutput(".trackerlock", Context.MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				osw.flush();
				osw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileWriter fileWriter = new FileWriter(file, true);
				fileWriter.append(msg+"\n ");
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
