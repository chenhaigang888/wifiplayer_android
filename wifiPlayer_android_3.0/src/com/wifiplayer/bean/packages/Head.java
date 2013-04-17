package com.wifiplayer.bean.packages;

import java.nio.ByteBuffer;

/**
 * 通讯包头
 * @author chenkaigang
 *
 */
public class Head {

	private short cmd;//	包标识：也可以叫命令，直接表示包的含义
	private int packBodyLenth;//	包长：表示整个包的长度
	private int channel;//	信道：保存当前socket id
	private int crc;//	crc：包体的crc32计算结果，用于检测包的准确性，如果不正确，则包被丢弃
	
	/**
	 * 连接服务器
	 */
	public static final short CONN_SERVER = 0x1001;
	/**
	 * 连接服务器返回
	 */
	public static final short CONN_SERVER_REPLY = 0x1002;
	/**
	 * 打开电脑的文件
	 */
	public static final short OPEN_FILE = 0x2001;
	/**
	 * 打开电脑的文件或文件夹回复信息
	 */
	public static final short OPEN_FILE_REPLY = 0x2002;
	/**
	 * 删除电脑上的文件 
	 */
	public static final short DEL_FILE = 0x2003;
	/**
	 * 删除电脑上的文件返回
	 */
	public static final short DEL_FILE_REPLY = 0x2004;
	/**
	 * 拷贝电脑上的文件到手机
	 */
	public static final short COPY_FILE_2_PHONE = 0x2005;
	/**
	 * 拷贝电脑上的文件到手机返回
	 */
	public static final short COPY_FILE_2_PHONE_REPLY = 0x2006;
	/**
	 * 打开文件夹
	 */
	public static final short OPEN_DIR = 0x2007;
	/**
	 * 打开文件夹返回
	 */
	public static final short OPEN_DIR_REPLY = 0x2008;
	
	public Head(short cmd, int packBodyLenth, int channel, int crc) {
		super();
		this.cmd = cmd;
		this.packBodyLenth = packBodyLenth;
		this.channel = channel;
		this.crc = crc;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public int getPackBodyLenth() {
		return packBodyLenth;
	}

	public void setPackBodyLenth(int packBodyLenth) {
		this.packBodyLenth = packBodyLenth;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getCrc() {
		return crc;
	}

	public void setCrc(int crc) {
		this.crc = crc;
	}

	/**
	 * 获取包头的字节数组
	 * @return
	 */
	public byte[] getBtye() {
		ByteBuffer buffer = ByteBuffer.allocate(14);
		buffer.putShort(0, cmd);
		buffer.putInt(2, packBodyLenth);
		buffer.putInt(6, channel);
		buffer.putInt(10, crc);
		
		byte[] head = new byte[14];
		buffer.get(head);
		return head;
	}
	
	/**
	 * 处理包头
	 * 
	 * @param heads
	 * @return
	 */
	public static Head resolveHead(byte[] heads) {
		ByteBuffer cmdBuffer = ByteBuffer.wrap(heads);
		short cmd = cmdBuffer.getShort(0);
		int packBodyLenth = cmdBuffer.getInt(2);
		int channel = cmdBuffer.getInt(6);
		int crc = cmdBuffer.getInt(10);
		Head head = new Head(cmd, packBodyLenth, channel, crc);
		return head;

	}
}
