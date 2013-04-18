package com.wifiplayer.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
/**
 * 寻找可以控制的pc
 * @author Administrator
 *
 */
public class SearchPc {
	static DatagramSocket ds;
	public static List<DatagramPacket> pcs = null;
	public static void connServer() throws IOException {
		pcs = new ArrayList<DatagramPacket> ();
		ds = new DatagramSocket(); 
		String sendStr = "Hello! I'm Client";
		byte[] sendBuf = sendStr.getBytes();
		InetAddress addr = InetAddress.getByName("192.168.1.255");
		int port = 9527;
		DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
		ds.send(sendDp);
	}
	
	/**
	 * 接收广播返回的内容
	 * @return
	 */
	public static List<DatagramPacket> receive() {
		byte[] recevBuf = new byte[100];
		DatagramPacket recvDp = new DatagramPacket(recevBuf, recevBuf.length);
		String recvStr = null;
		try {
			ds.setSoTimeout(3000);
			ds.receive(recvDp);
			recvStr = new String(recvDp.getData(), 0, recvDp.getLength());
			Log.i("receive", "接收到的内容：" + recvStr);
			pcs.add(recvDp);
			receive();
		} catch (SocketException e) {
			e.printStackTrace();
			ds.close();
			return pcs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pcs;
	}
}
