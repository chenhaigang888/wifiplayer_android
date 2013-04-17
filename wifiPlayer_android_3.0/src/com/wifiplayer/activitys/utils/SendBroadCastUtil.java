package com.wifiplayer.activitys.utils;


import com.wifiplayer.bean.ReqReplyOp;

import android.content.Context;
import android.content.Intent;
/**
 * 发送广播类
 * @author Administrator
 *
 */
public class SendBroadCastUtil {
	
	/**
	 * 发送广播
	 * @param context
	 * @param srr 服务器返回的结果对象
	 */
	public static void sendBroadCast(Context context, ReqReplyOp rro) {
		Intent intent = new Intent("com.wifiplayer.activitys.FunctionActivity");
		intent.putExtra("rro", rro);
		context.sendBroadcast(intent);
		
	}
}
