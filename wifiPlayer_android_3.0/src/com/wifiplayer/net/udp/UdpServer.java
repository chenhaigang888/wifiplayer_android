package com.wifiplayer.net.udp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Build;
import android.util.Log;


/**
 * UDP服务器 用户客户端寻找Server使用
 * @author Administrator
 *
 */
public class UdpServer extends Thread{
	
	public static final int PORT = 9527;

	DatagramSocket ds = null;
	DatagramPacket recevDp = null;
	public void startUdpServer () throws IOException {
		byte[] receBuf = new byte[100];
		ds = new DatagramSocket(PORT);//UDPsocket
		recevDp = new DatagramPacket(receBuf, receBuf.length);
		
		startReceive();
		
	}

	private void startReceive() {
		Log.i("receive", "android 启动监听");
		// TODO Auto-generated method stub
		try {
			ds.receive(recevDp);
			String recvStr = new String(recevDp.getData(), 0, recevDp.getLength());
			System.out.println("接收到的UDP内容_android：" + recvStr);
			/*发送回复信息*/
			String ip = ds.getInetAddress() + "";
			ip = ip.replace("/", "");//对方的ip
			InetAddress ia = InetAddress.getLocalHost();
			String myIp = (ia + "").replace("/", "");;
			Log.i("receive", "我的ipmyIp:" + myIp);
			Log.i("receive", "对方的Ip:" + ip);
			if (ip.equals(myIp)) {
				
			} else {
				int port = recevDp.getPort();//发送者的端口
				InetAddress addr = recevDp.getAddress();//发送者的ip地址
				String[] sysInfo = getVersion();
				String sendStr = sysInfo[3] + "&" + sysInfo[1] + "&" + sysInfo[2];
				byte[] sendBuf = sendStr.getBytes();
				DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
				ds.send(sendDp);
			}
			ds.close();
			startUdpServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//启动监听
		
	}

	@Override
	public void run() {
		try {
			startUdpServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getVersion(){  
	    String[] version={"null","null","null","null"};  
	    String str1 = "/proc/version";  
	    String str2;  
	    String[] arrayOfString;  
	    try {  
	        FileReader localFileReader = new FileReader(str1);  
	        BufferedReader localBufferedReader = new BufferedReader( localFileReader, 8192);  
	        str2 = localBufferedReader.readLine();  
	        arrayOfString = str2.split("\\s+");  
	        version[0]=arrayOfString[2];//KernelVersion  
	        localBufferedReader.close();  
	    } catch (IOException e) {  
	    }  
	    version[1] = Build.VERSION.RELEASE;// firmware version  
	    version[2]=Build.MODEL;//model  
	    
	    version[3]="android";//system version  
	    
	    
	    return version;  

	} 
	
	
}
