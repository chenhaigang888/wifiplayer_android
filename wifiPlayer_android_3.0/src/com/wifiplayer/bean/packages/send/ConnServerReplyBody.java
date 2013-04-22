package com.wifiplayer.bean.packages.send;

import com.wifiplayer.bean.packages.Body;
/**
 * 服务器连接返回
 * @author Administrator
 *
 */
public class ConnServerReplyBody extends Body {

	private byte[] bodys;
	
	public ConnServerReplyBody(byte[] bodys) {
		super();
		this.bodys = bodys;
	}

	@Override
	public byte[] packet() {
		
		return bodys;
	}

}
