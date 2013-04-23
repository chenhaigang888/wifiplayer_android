package com.wifiplayer.net.tcp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.activitys.views.FileOpView;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.packages.Head;

import android.content.Context;
import android.os.Environment;
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
				return len;
			}
			if ((len + readPosition) < array.length) {
				readData(s, len + readPosition, array);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
				delFileReply(bodyArray);
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
	 * 删除失败的时候返回的信息
	 * 
	 * @param bodyArray
	 */
	private void delFileReply(byte[] bodyArray) {

		String bodyStr = new String(bodyArray);
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.DEL_FILE_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(1);
		SendBroadCastUtil.sendBroadCast(context, rro);
	}

	int sendSecond = 0;// 纪录更新pragressBar的次数
	Timer t = null;
	HashMap<String, String> map = null;
	long currReadLenth = 0; // 已经读取的长度
	Message msg = null;

	/**
	 * 将pc的文件拷贝到手机内
	 * 
	 * @param head
	 */
	private void copyFile2PhoneReply(Head head) {
		
		File document = new File(Environment.getExternalStorageDirectory() + "/wifiPlayer/");
		document.mkdirs();// 创建文件夹
		map = new HashMap<String, String>();
		try {
			RandomAccessFile raf = new RandomAccessFile(document.getAbsolutePath() + "/" + PcOpManager.copyFileName, "rw");// "rw":以读写方式打开指定文件，不存在就创建新文件。
			byte[] bodyArray = readBody(head);
			Log.i("receive", "String(bodyArray):" + new String(bodyArray));
			final long fileLenth = new Long(new String(bodyArray));
			raf.setLength(fileLenth);// 设置需要下载的文件的大小
			raf.seek(0);// 设置从文件的什么位置开始写入
			t = new Timer();

			InputStream is = socket.getInputStream();
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;

			while ((len = is.read(buffer, 0, buffer.length)) != -1) {
				currReadLenth += len;
				raf.write(buffer, 0, len);

				if (currReadLenth == fileLenth) {
					msg = new Message();
					msg.setTarget(FileOpView.downLoadHandler);
//					map = new HashMap<String, String>();
					map.put("total", fileLenth + "");
					map.put("curr", fileLenth + "");
					map.put("finish", "finish");
					msg.obj = map;
					msg.sendToTarget();
//					map = null;
					t = null;
					currReadLenth = 0;
					break;
				} else {
					if (t != null) {
						t.schedule(new TimerTask() {

							@Override
							public void run() {
//								map = new HashMap<String, String>();
								if (map != null) {
									map.put("total", fileLenth + "");
									map.put("curr", currReadLenth + "");
									map.put("finish", "");

									msg = new Message();
									msg.setTarget(FileOpView.downLoadHandler);
									msg.obj = map;
									msg.sendToTarget();
									sendSecond++;
									if (sendSecond == 1000) {
										sendSecond = 0;
										System.gc();
									}
//									map = null;
									msg = null;
								}
								
							}
						}, 1000);
					}
					
				}

				
			}
			raf.close();
//			t = null;
			msg = null;
			map = null;
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
	 * 
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
