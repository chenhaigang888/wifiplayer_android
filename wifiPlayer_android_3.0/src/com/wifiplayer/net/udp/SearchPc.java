package com.wifiplayer.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.wifiplayer.bean.FindedPC;

import android.util.Log;
/**
 * 寻找可以控制的pc
 * @author Administrator
 *
 */
public class SearchPc {
	public static String os = null;
	static DatagramSocket ds;
	public static List<FindedPC> pcs = null;
	public static void connServer(String ipAddr) throws IOException {
		pcs = new ArrayList<FindedPC> ();
		ds = new DatagramSocket(); 
		String sendStr = "Hello! I'm Client";
		byte[] sendBuf = sendStr.getBytes();
		InetAddress addr = InetAddress.getByName(ipAddr);
		int port = 9527;
		DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
		ds.send(sendDp);
	}
	
	/**
	 * 接收广播返回的内容
	 * @return
	 */
	public static List<FindedPC> receive() {
		byte[] recevBuf = new byte[100];
		DatagramPacket recvDp = new DatagramPacket(recevBuf, recevBuf.length);
		FindedPC fPC = null;
		String recvStr = null;
		try {
			ds.setSoTimeout(3000);
			ds.receive(recvDp);
			
			recvStr = new String(recvDp.getData(), 0, recvDp.getLength());
			Log.i("receive", "接收到的内容：" + recvStr);
			String[] propers = recvStr.split("&");
			fPC = new FindedPC();
			fPC.setDatagramPacket(recvDp);
			os = propers[0];
			fPC.setOsName(propers[0]);
			fPC.setVersion(propers[1]);
			fPC.setPcUser(propers[2]);
			pcs.add(fPC);
			receive();
		} catch (SocketException e) {
			e.printStackTrace();
			ds.close();
			return pcs;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		
		return pcs;
	}
}
