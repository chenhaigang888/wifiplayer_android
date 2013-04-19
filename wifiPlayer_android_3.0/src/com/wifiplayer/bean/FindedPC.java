package com.wifiplayer.bean;

import java.net.DatagramPacket;

/**
 * 寻找到的电脑
 * @author chenkaigang
 *
 */
public class FindedPC {

	private String osName;//操作系统名
	private String version;//操作系统版本号 
	private String pcUser;//用户名
	private DatagramPacket datagramPacket;//电脑连接信息
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPcUser() {
		return pcUser;
	}
	public void setPcUser(String pcUser) {
		this.pcUser = pcUser;
	}
	public DatagramPacket getDatagramPacket() {
		return datagramPacket;
	}
	public void setDatagramPacket(DatagramPacket datagramPacket) {
		this.datagramPacket = datagramPacket;
	}
	public FindedPC(String osName, String version, String pcUser,
			DatagramPacket datagramPacket) {
		super();
		this.osName = osName;
		this.version = version;
		this.pcUser = pcUser;
		this.datagramPacket = datagramPacket;
	}
	public FindedPC() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FindedPC [osName=" + osName + ", version=" + version
				+ ", pcUser=" + pcUser + ", datagramPacket=" + datagramPacket
				+ "]";
	}
	
	
}
