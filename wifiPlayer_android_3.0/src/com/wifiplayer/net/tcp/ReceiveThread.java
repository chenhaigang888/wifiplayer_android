package com.wifiplayer.net.tcp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.HashMap;

import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.packages.Head;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 接收服务器发来的信息
 * 
 * @author Administrator
 * 
 */
public class ReceiveThread extends Thread {
 
	private Socket socket;
	private Context context;
	boolean isReceive = true;

	public ReceiveThread(Socket socket, Context context) {
		super();
		this.socket = socket;
		this.context = context;
	}

	@Override
	public void run() {
		Log.i("receive", "线程中开始监听时socket:" + socket);
		receive_();
		Log.i("receive", "Receive线程中监听结束");
	}

	/**
	 * 打开文件夹返回
	 * 
	 * @param bodyArray
	 */
	private void openDirReply(byte[] bodyArray) {
		// TODO Auto-generated method stub
		connServerReply(bodyArray);
	}

	/**
	 * 连接服务器返回信息
	 * 
	 * @param bodyArray
	 */
	private void connServerReply(byte[] bodyArray) {
		
		String bodyStr = new String(bodyArray);
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.CONN_SERVER_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(0);
		SendBroadCastUtil.sendBroadCast(context, rro);
	}

	public int readData(Socket s, int readPosition, byte[] array) {
		int len = 0;
		InputStream is = null;
		try {
			is = s.getInputStream();
			len = is.read(array, readPosition, array.length - readPosition);
			if (len == -1) {
//				ConnServer.close();
				Log.i("receive", "Receive线程readData中Socket:" + s);
				return len;
			}
			if ((len + readPosition) < array.length) {
				readData(s, len + readPosition, array);
			}
		} catch (IOException e) {
			e.printStackTrace();
//			ConnServer.close();
			return -1;
		}

		return len + readPosition;
	}

	public void receive_() {
		while (isReceive) {
			byte[] headArray = new byte[14];
			int len = readData(socket, 0, headArray);
			if (len == -1) {
				isReceive = false;
				break;
			}
			
			Head head = Head.resolveHead(headArray);
			
			byte[] bodyArray = null;
			switch (head.getCmd()) {
			case Head.CONN_SERVER_REPLY:// 连接服务器返回
				bodyArray = readBody(head);
				connServerReply(bodyArray);
				break;
			case Head.COPY_FILE_2_PHONE_REPLY:// 拷贝文件到手机上返回
				copyFile2PhoneReply(head);
				break;
			case Head.DEL_FILE_REPLY:// 删除文件返回
				break;
			case Head.OPEN_FILE_REPLY:// 打开文件返回
				bodyArray = readBody(head);
				openFileReply(bodyArray);
				break;
			case Head.OPEN_DIR_REPLY:// 打开文件夹返回
				bodyArray = readBody(head);
				openDirReply(bodyArray);
				break;
			default:
				break;

			}
		}
		
	}
	
	/**
	 * 将pc的文件拷贝到手机内
	 * @param head
	 */
	private void copyFile2PhoneReply(Head head) {
		File document = new File(Environment.getExternalStorageDirectory()+"/wifiPlayer/");
		document.mkdirs();//创建文件夹
		try {
			RandomAccessFile raf = new RandomAccessFile(document.getAbsolutePath() + "/" +PcOpManager.copyFileName, "rw");//"rw":以读写方式打开指定文件，不存在就创建新文件。
			byte[] bodyArray = readBody(head);
			long fileLenth = new Long(new String(bodyArray));
			
			raf.setLength(fileLenth);//设置需要下载的文件的大小
			raf.seek(0);//设置从文件的什么位置开始写入
			
			
			InputStream is = socket.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			long currReadLenth = 0; //已经读取的长度
			while((len = is.read(buffer, 0, 1024)) != -1) {
				currReadLenth += len;
				raf.write(buffer, 0, len);
				System.out.println("当前读取的文件的内容的长度：" + currReadLenth);
//				downLoadHandler
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("total", fileLenth/1024/1024 + "");
				map.put("curr", currReadLenth/1024/1024 + "");
				
				map.put("finish", "");
				Message msg = new Message();
				
				msg.setTarget(FunctionActivity.downLoadHandler);
				
				if (currReadLenth == fileLenth) {
					map.put("total", fileLenth/1024/1024 + "");
					map.put("curr", currReadLenth/1024/1024 + "");
					
					map.put("finish", "finish");
					
					msg.obj = map;
					msg.sendToTarget();
					break;
				}
				
				msg.obj = map;
				msg.sendToTarget();
				
			}
			raf.close();
			
			ReqReplyOp rro = new ReqReplyOp();
			rro.setCmd(Head.COPY_FILE_2_PHONE_REPLY);
			rro.setContent("下载完成!");
			rro.setStatus(0);
			SendBroadCastUtil.sendBroadCast(context, rro);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] readBody(Head head) {
		byte[] bodyArray = new byte[head.getPackBodyLenth()];
		int len = readData(socket, 0, bodyArray);
		if (len == -1) {
			isReceive = false;
			return null;
		}
		return bodyArray;
	}
	
	

	/**
	 * 打开文件返回
	 * @param bodyArray
	 */
	private void openFileReply(byte[] bodyArray) {
		String bodyStr = new String(bodyArray);
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.OPEN_FILE_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(0);
		SendBroadCastUtil.sendBroadCast(context, rro);
		
	}

}
