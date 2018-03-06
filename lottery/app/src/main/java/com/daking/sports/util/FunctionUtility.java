package com.daking.sports.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;


/**
 * 
 * @ClassName: FunctionUtility
 * @Description: (下载到手机的地址)
 *
 */
public class FunctionUtility {

	private static Context mContext;

	public static void setContext(Context context) {
		mContext = context;
	}

	public static Context getContext() {
		return mContext;
	}

	/**
	 * 下载到SD卡位置
	 */
	public static String getUpgradePath() {

		String filePath = getAppRootPath() + "/upgrade/";
		File file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		file = null;
		return filePath;
	}

	public static String getAppRootPath() {
		String filePath = "/lottery";
		if (ExistSDCard()) {
			filePath = Environment.getExternalStorageDirectory() + filePath;
		} else {
			filePath = getContext().getCacheDir() + filePath;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		File nomedia = new File(filePath + "/.nomedia");
		if (!nomedia.exists()) {
			try {
				nomedia.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}

	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}
}
