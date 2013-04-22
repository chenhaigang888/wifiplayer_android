package com.wifiplayer.net.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务管理
 * 
 * @author Administrator
 * 
 */
public class ServerManager {

	private ExecutorService executorService;// 线程池
	public boolean isStart = true;

	public ServerManager() {
		executorService = Executors.newFixedThreadPool(5);
	}

	/**
	 * 启动服务
	 */
	public void startServer() {
		new SocketTask(9528).start();
		System.err.println("服务启动,等待用户连接...");
	}

	private class SocketTask extends Thread {

		private int port;

		public SocketTask(int port) {
			super();
			this.port = port;
		}

		@Override
		public void run() {
			ServerSocket ss = null;
			Socket s = null;
			try {
				ss = new ServerSocket(port);
				while (isStart) {
					s = ss.accept();
					System.out.println("一个用户连接");
					executorService.execute(new ReceiveThread(s));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (s != null) {
						s.close();
					}
					if (ss != null) {
						ss.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
