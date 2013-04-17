package com.wifiplayer.bean.packages.send;

import com.wifiplayer.bean.packages.Body;
/**
 * 打开文件夹
 * @author Administrator
 *
 */
public class OpenDirBody extends Body {

	byte[] body;
	
	
	
	public OpenDirBody(byte[] body) {
		super();
		this.body = body;
	}



	@Override
	public byte[] packet() {
		// TODO Auto-generated method stub
		return body;
	}
}
