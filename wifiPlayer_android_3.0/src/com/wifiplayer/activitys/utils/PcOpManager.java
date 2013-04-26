package com.wifiplayer.activitys.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;

import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.bean.packages.Packages;
import com.wifiplayer.bean.packages.send.OpenDirBody;
import com.wifiplayer.net.tcp.ConnServer;
import com.wifiplayer.net.udp.SearchPc;

/**
 * 电脑操作管理
 * @author Administrator
 *
 */
public class PcOpManager { 
	
	public static String copyFileName = null;
	
	/**
	 * 连接电脑
	 * @param addr
	 * @param context
	 */
	public static void connServer(InetAddress addr, Context context) {
		ConnServer.conn(addr, context);
	}
	
	/**
	 * 连接电脑
	 * @param addr
	 * @param context
	 * @throws UnknownHostException
	 */
	public static void connServer(final String addr, final Context context) throws UnknownHostException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				InetAddress addrs;
				try {
					addrs = InetAddress.getByName(addr);
					connServer(addrs, context);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

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
	 * 将文件从pc上拷贝到手机
	 * @param context
	 * @param path
	 */
	public static void copyFile2Phone(Context context, String path) {
		if (SearchPc.os.equals("Mac OS X")) {
			copyFileName = path.substring(path.lastIndexOf("/") + 1, path.length());
		} else if (SearchPc.os.equals("android")) {
			copyFileName = path.substring(path.lastIndexOf("/") + 1, path.length());
		} else {
			copyFileName = path.substring(path.lastIndexOf("\\") + 1, path.length());
		}
		sendOpCMD(context, Head.COPY_FILE_2_PHONE, path);
	}
	
	/**
	 * 发送简单的操作指令
	 * @param context
	 * @param cmd
	 * @param path
	 */
	private static void sendOpCMD(Context context, int cmd, String path) {
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
