package com.wifiplayer.activitys.utils;

import java.io.Serializable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SendHandlerMsg {

	public void sendHandler(Handler handler,String key,String str){
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString(key, str);
		msg.setData(data);
		msg.setTarget(handler);
		msg.sendToTarget();
	}
	/**
	 * 发�?�?��对象到相应的handler
	 * @param handler
	 * @param key
	 * @param o
	 */
	public void sendHandler(Handler handler,String key,Object o){
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putSerializable(key, (Serializable) o);
		msg.setData(data);
		msg.setTarget(handler);
		msg.sendToTarget();
	}
	
}
