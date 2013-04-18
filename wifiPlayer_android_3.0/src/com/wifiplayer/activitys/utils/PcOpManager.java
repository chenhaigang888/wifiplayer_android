package com.wifiplayer.activitys.utils;

import android.content.Context;

import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.bean.packages.Packages;
import com.wifiplayer.bean.packages.send.OpenDirBody;
import com.wifiplayer.net.tcp.ConnServer;

/**
 * 电脑操作管理
 * @author Administrator
 *
 */
public class PcOpManager { 

	/**
	 * 打开文件夹
	 * @param dirPath
	 */
	public static void openDir(String dirPath, Context context) {
		sendOpCMD(context, Head.OPEN_DIR, dirPath);
		FunctionActivity.pd.showPromptDialog(R.string.please_wait);
	}
	/**
	 * 进入主目录
	 * @param dirPath
	 */
	public static void openMainDir(Context context, boolean firstConn) {
		sendOpCMD(context, Head.CONN_SERVER, null);
		if (!firstConn) {
			FunctionActivity.pd.showPromptDialog(R.string.please_wait);
		}
	}
	
	/**
	 * 删除文件、文件夹
	 */
	public static void delFile(Context context, String path) {
		sendOpCMD(context, Head.DEL_FILE, path);
		FunctionActivity.pd.showPromptDialog(R.string.please_wait);
	}
	
	/**
	 * 打开文件
	 * @param context
	 * @param path
	 */
	public static void openFile(Context context, String path) {
		sendOpCMD(context, Head.OPEN_FILE, path);
		FunctionActivity.pd.showPromptDialog(R.string.please_wait);
	}
	
	/**
	 * 发送简单的操作指令
	 * @param context
	 * @param cmd
	 * @param path
	 */
	private static void sendOpCMD(Context context, short cmd, String path) {
		byte[] pathArr = null;
		int pathArrLenth = 0;
		OpenDirBody odb = null;
		if (path != null) {
			pathArr = path.getBytes();
			pathArrLenth = pathArr.length;
			odb = new OpenDirBody(pathArr);
		}
		Head head = new Head(cmd, pathArrLenth, 0, 0);
		Packages p = new Packages(head, odb);
		ConnServer.send(p.getPackage(), context);
		
	}
	
}
