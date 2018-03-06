package com.daking.sports.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 
 * @ClassName: AppUtils
 * @Description: TODO(APP中使用到的公共方法)
 * @author hyx18670335751@163.com
 * @date 2015年12月8日 上午9:50:38
 * 
 */
@SuppressLint("NewApi")
public class AppUtils {
	// 拿到手机的UserAgent字段
	public static String getUserAgent(Context context) {
		WebView webview = new WebView(context);
		webview.layout(0, 0, 0, 0);
		WebSettings settings = webview.getSettings();
		String ua = settings.getUserAgentString();
		return ua;
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	public static String getVersionName(Context context) {
		PackageManager manager;
		PackageInfo info = null;
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	public static String jointStr(String content, int count) {
		int length = content.length();
		String str = "";
		if (count > length) {
			int sum = count - length;
			str = content;
			for (int i = 0; i < sum; i++) {
				str = str + "0";
			}
		} else if (count == length) {
			str = content;
		} else {
			str = content.substring(0, count);
		}
		return str;
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月9日 上午11:18:08
	 * @Title: getUTF8XMLString
	 * @Description: TODO(转UTF8编码)
	 * @param @param xml
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUTF8XMLString(String xml) {
		StringBuffer sb = new StringBuffer();
		sb.append(xml);
		String xmString = "";
		String xmlUTF8 = "";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
			xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return xmlUTF8;
	}


    public static String getPhoneNumber(Context mContext) {
        TelephonyManager phoneMgr = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getLine1Number();
    }

    public static String getDeviceID(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getMacAddress(Context mContext) {
        WifiManager wifi = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String address = info.getMacAddress();
        if(address != null && address.length() > 0){
            address = address.replace(":", "");
        }
        return address;
    }

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "QAZWSXEDCRFVTGBYHNUJMIKLOPabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/*
	 * public static Camera.Size getNearestRatioSize(Camera.Parameters para,
	 * final int screenWidth, final int screenHeight) { List<Camera.Size>
	 * supportedSize = para.getSupportedPreviewSizes();
	 * Collections.sort(supportedSize, new Comparator<Camera.Size>() {
	 * 
	 * @Override public int compare(Camera.Size lhs, Camera.Size rhs) { int
	 * diff1 = (((int) ((1000 * (Math.abs(lhs.width / (float) lhs.height -
	 * screenWidth / (float) screenHeight))))) << 16) + lhs.width; int diff2 =
	 * (((int) (1000 * (Math.abs(rhs.width / (float) rhs.height - screenWidth /
	 * (float) screenHeight)))) << 16) + rhs.width;
	 * 
	 * return diff1 - diff2; } });
	 * 
	 * return supportedSize.get(0); }
	 */

	public static Camera.Size getNearestRatioSize(Camera.Parameters para,
                                                  final int screenWidth, final int screenHeight) {
		List<Camera.Size> supportedSize = para.getSupportedPreviewSizes();
		for (Camera.Size tmp : supportedSize) {
			if (tmp.width == 1280 && tmp.height == 720) {
				return tmp;
			}
		}
		Collections.sort(supportedSize, new Comparator<Camera.Size>() {
			@Override
			public int compare(Camera.Size lhs, Camera.Size rhs) {
				int diff1 = (((int) ((1000 * (Math.abs(lhs.width
						/ (float) lhs.height - screenWidth
						/ (float) screenHeight))))) << 16)
						- lhs.width;
				int diff2 = (((int) (1000 * (Math.abs(rhs.width
						/ (float) rhs.height - screenWidth
						/ (float) screenHeight)))) << 16)
						- rhs.width;

				return diff1 - diff2;
			}
		});

		return supportedSize.get(0);
	}

	public static String getTimeStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddhhmmss");
		return simpleDateFormat.format(new Date());
	}

	public static void closeStreamSilently(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {

			}
		}
	}

	public static byte[] bmp2byteArr(Bitmap bmp) {
		if (bmp == null || bmp.isRecycled())
			return null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
		AppUtils.closeStreamSilently(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}



	public static byte[] readModel(Context context) {
		InputStream inputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count = -1;
		try {
			inputStream = context.getAssets().open("idcardquality_model");
			while ((count = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, count);
			}
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteArrayOutputStream.toByteArray();
	}



	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月15日 下午3:24:29
	 * @Title: saveBitmapFile
	 * @Description: TODO(将bitmap转换为文件保存)
	 * @param @param bitmap 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void saveBitmapFile(Bitmap bitmap, String fileUrl,
                                      String imgName) {
		File dir = new File(fileUrl);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			File file = new File(dir, imgName); // 将要保存图片的路径
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月15日 下午3:29:32
	 * @Title: ExistSDCard
	 * @Description: TODO(判断是否存在SDCard)
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月15日 下午7:16:02
	 * @Title: fileIsExists
	 * @Description: TODO(判断文件是否存在)
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean fileIsExists(String fileUrl) {
		try {
			File f = new File(fileUrl);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月15日 下午7:16:02
	 * @Title: fileIsExists
	 * @Description: TODO(获取文件)
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static File fileGainExists(String fileUrl) {
		try {
			File f = new File(fileUrl);
			if (f.exists()) {
				return f;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2015年12月15日 下午7:20:18
	 * @Title: deleteFile
	 * @Description: TODO(删除文件)
	 * @param @param root 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static boolean deleteFile(String fileUrl) {
		File file = new File(fileUrl);
		if (file.exists()) { // 判断是否存在
			try {
				boolean isSucceed = file.delete();
				return isSucceed;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param filePath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				// 为文件时调用删除文件方法
				return deleteFile(filePath);
			} else {
				// 为目录时调用删除目录方法
				return deleteDirectory(filePath);
			}
		}
	}

	/**
	 * 删除文件夹以及目录下的文件
	 * 
	 * @param filePath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String filePath) {
		boolean flag = false;
		// 如果filePath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();
		// 遍历删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前空目录
		return dirFile.delete();
	}

	/**
	 * 时间格式化(格式到秒)
	 */
	public static String getFormatterTime(long time) {
		Date d = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String data = formatter.format(d);
		return data;
	}

	/**
	 * 
	 * @author hyx18670335751@163.com
	 * @date 2016年1月12日 下午6:03:47
	 * @Title: isWeixinAvilible
	 * @Description: TODO(判断是否安装了微信)
	 * @param @param context
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isWeixinAvilible(Context context) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}

		return false;
	}

	// 判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE http://0nly3nd.sinaapp.com/?p=368
	public static boolean isEmulator(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (imei != null && imei.equals("000000000000000")) {
				return true;
			}
			return (Build.MODEL.equals("sdk"))
					|| (Build.MODEL.equals("google_sdk"));
		} catch (Exception ioe) {

		}
		return false;
	}

	/**
	 * 判断某个界面是否在前台
	 *
	 * @param context
	 * @param className 某个界面名称
	 */
	public static boolean isForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;

			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}

		return false;
	}

	/*
	复制功能
	 */
	public static void copyText(Context context, String text) {
		ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("text", text);
		clipboardManager.setPrimaryClip(clipData);
	}

	/**
	 * 返回当前的应用是否处于前台显示状态
	 * @param $packageName
	 * @return
	 */
	public static boolean isTopActivity(Context context, String $packageName)
	{
		//_context是一个保存的上下文
		ActivityManager __am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> __list = __am.getRunningAppProcesses();
		if(__list.size() == 0) return false;
		for(ActivityManager.RunningAppProcessInfo __process:__list) {
			if(__process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
					__process.processName.equals($packageName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前版本号
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "1.0";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "1.0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
}
