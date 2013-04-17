package com.wifiplayer.activitys.utils;

import android.content.Context;

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
		byte[] pathArr = dirPath.getBytes();
		Head head = new Head(Head.OPEN_DIR, (short)pathArr.length, 0, 0);
		OpenDirBody odb = new OpenDirBody(pathArr);
		Packages p = new Packages(head, odb);
		ConnServer.send(p.getPackage(), context);
	}
	/**
	 * 进入主目录
	 * @param dirPath
	 */
	public static void openMainDir(Context context) {
		Head head = new Head(Head.CONN_SERVER, (short)0, 0, 0);
		Packages p = new Packages(head, null);
		ConnServer.send(p.getPackage(), context);
	}
	
}
