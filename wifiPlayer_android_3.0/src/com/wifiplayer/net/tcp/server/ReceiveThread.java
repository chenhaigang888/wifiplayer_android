package com.wifiplayer.net.tcp.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.bean.packages.Packages;
import com.wifiplayer.bean.packages.send.ConnServerReplyBody;
import com.wifiplayer.utils.ReadDirectoryFile;


/**
 * 接收线程
 * 
 * @author Administrator
 * 
 */
public class ReceiveThread implements Runnable {

	Socket s;
	public boolean isReceive = true;
	private RandomAccessFile raf;

	public ReceiveThread(Socket s) {
		super();
		this.s = s;
	}

	@Override
	public void run() {
		receive();
		System.out.println("监听结束");
	}

	private void receive() {
		while (isReceive) {
			System.out.println("isReceive:" + isReceive);
			
			byte[] headArray = new byte[14];
			int len = readData(s, 0, headArray);
			System.out.println("len:" + len);
			if (len == -1) {
				isReceive = false;
				break;
			}
//			System.out.println("headArray--:" + new String(headArray));
//			new SendThread(s, "你好啊哈哈哈哈".getBytes()).start();//ios测试使用的代码
			
			
			Head head = Head.resolveHead(headArray);
			byte[] bodyArray = new byte[head.getPackBodyLenth()];
			len = readData(s, 0, bodyArray);
			if (len == -1) {
				isReceive = false;
				break;
			}
			switch (head.getCmd()) {
			case Head.CONN_SERVER:// 客户端连接服务器
				connServer();
				break;
			case Head.OPEN_DIR:
				openDir(new String(bodyArray), Head.OPEN_DIR_REPLY);
				break;
			case Head.DEL_FILE:
//				delFile(new String(bodyArray));
				break;
			case Head.OPEN_FILE:
//				openFile(new String(bodyArray));
				break;
			case Head.COPY_FILE_2_PHONE:
				copyFile2Phone(bodyArray);
				break;
			default:
				break;
			}

		}
		
	}

	/**
	 * 拷贝文件到手机
	 * @param head
	 */
	private void copyFile2Phone(final byte[] bodyArray) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					File uploadFile = new File(new String(bodyArray));//需要上传的文件
					long upLoadFileLenth = uploadFile.length();//需要上传的长度
					
					byte[] connServerArray = (upLoadFileLenth + "").getBytes();
					Head head = new Head(Head.COPY_FILE_2_PHONE_REPLY, connServerArray.length, 0, 0);
					ConnServerReplyBody csrb = new ConnServerReplyBody(connServerArray);
					Packages p = new Packages(head, csrb);
					OutputStream os = s.getOutputStream();
					os.write(p.getPackage());
					os.flush();
					
					long alreadyUpload = 0;//已经上传的长度
					int len = 0; //当前读取的长度
					raf = new RandomAccessFile(uploadFile, "r");
					raf.seek(0);//设置从文件的什么位子开始读取
						
						byte[] buffer = new byte[1024*1024];
						while ((len = raf.read(buffer, 0, buffer.length)) != -1) {
							alreadyUpload += len;
//							os = s.getOutputStream();
							os.write(buffer, 0, len);
							os.flush();
//							System.out.println("已经上传的长度:"+ alreadyUpload);
						}
						raf.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}).start();
		
		
	}

//	/**
//	 * 删除文件
//	 * @param string 文件路径
//	 */
//	private void delFile(String path) {
//		File file = new File(path);
//		boolean delResult = ReadDirectoryFile.delFile(file);
//		System.out.println("删除成功?" + delResult);
//		if (delResult) {
//			if (Main_.osName.equals("Mac OS X")) {//如果是苹果系统
//				path = path.replace("\\", "/");
//			} else {
//				path = path.replace("/", "\\");
//			}
//			path = path.substring(0, path.lastIndexOf(Main_.separator));
//			openDir(path, Head.OPEN_DIR_REPLY);
//			return;
//		} else {
//			byte[] connServerArray = (delResult + "").getBytes();
//			System.out.println("删除结果：" + new String(connServerArray));
//			Head head = new Head(Head.DEL_FILE_REPLY, connServerArray.length, 0, 0);
//			ConnServerReplyBody csrb = new ConnServerReplyBody(connServerArray);
//			Packages p = new Packages(head, csrb);
//			new SendThread(s, p.getPackage()).start();
//		}
//		
//	}
	boolean playResult = false;
	
	/**
	 * 播放视频(音乐)文件
	 * @param path
	 */
//	private void openFile(final String path) {
//	
//		if (Main_.osName.equals("Mac OS X")) {
//			playResult = OpenFile.macPlayer(path);
//		} else {
//			final String hzm = path.substring(path.lastIndexOf(".")+1, path.length()).toLowerCase();//得到文件的后缀名
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					
//					try {
//						if(hzm.equals("mp3")||hzm.equals("wma")||hzm.equals("wav")||hzm.equals("ape")||hzm.equals("flac")||hzm.equals("aac")||hzm.equals("cue")){
//							playResult = OpenFile.playMusic(path, 0);
//						}else{
//							playResult = OpenFile.playMovie(path, 0);
//						}
//					} catch (Exception e) {
//						System.out.println("打开文件失败");
//					} finally {
//						sendPlayResult();
//					}
//					
//				}
//			}).start();
//		}
//		sendPlayResult();
//	}
	
	
	/**
	 * 发送播放信息
	 */
	private void sendPlayResult() {
		byte[] connServerArray = (playResult + "").getBytes();
		Head head = new Head(Head.OPEN_FILE_REPLY, connServerArray.length, 0, 0);
		ConnServerReplyBody csrb = new ConnServerReplyBody(connServerArray);
		Packages p = new Packages(head, csrb);
		new SendThread(s, p.getPackage()).start();
		
	}

	/**
	 * 读取数据
	 * @param s socket
	 * @param readPosition 从什么地方开始读
	 * @param array 需要读入的数组
	 * @return
	 */
	public int readData(Socket s, int readPosition, byte[] array) {
		int len = 0;
		InputStream is = null;
		try {
			is = s.getInputStream();
			len = is.read(array, readPosition, array.length - readPosition);
			if (len == -1) {
				close(s, is);
				return len;
			}
			if ((len + readPosition) < array.length) {
				readData(s, len + readPosition, array);
			}
		} catch (IOException e) {
			e.printStackTrace();
			close(s, is);
		}

		return len + readPosition;
	}

	/**
	 * 关闭资源
	 * 
	 * @param s2
	 * @param is
	 */
	private void close(Socket s2, InputStream is) {
		try {
			isReceive = false;
			s.close();
			is.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 获取文件夹
	 */
	private void openDir(String path, short cmd) {
		try {
			String str = ReadDirectoryFile.listFile(path).toString();
			System.out.println("str:" + str);
			byte[] connServerArray = str.getBytes();
			Head head = new Head(cmd, connServerArray.length, 0, 0);
			ConnServerReplyBody csrb = new ConnServerReplyBody(connServerArray);
			Packages p = new Packages(head, csrb);
			new SendThread(s, p.getPackage()).start();
		} catch (Exception e) {
			System.out.println("没有找到相关内容");
		}

	}

	/**
	 * 客户端连接服务器
	 */
	private void connServer() {
		String str = ReadDirectoryFile.systemRoots().toString();
		System.out.println("根目录：" + str);
		byte[] connServerArray = str.getBytes();
		Head head = new Head(Head.CONN_SERVER_REPLY, connServerArray.length, 0, 0);
		ConnServerReplyBody csrb = new ConnServerReplyBody(connServerArray);
		Packages p = new Packages(head, csrb);
		new SendThread(s, p.getPackage()).start();
	}

}
