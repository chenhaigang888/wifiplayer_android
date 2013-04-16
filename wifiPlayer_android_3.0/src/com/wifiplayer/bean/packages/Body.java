package com.wifiplayer.bean.packages;
/**
 * 通信包体
 * @author Administrator
 *
 */
public abstract class Body {

	/**
	 * 封包，并且返回字节数组
	 * @return
	 */
	public abstract byte[] packet();
	
}
