package com.wifiplayer.bean.packages;
/**
 * 包
 * 通信协议中 包分为包头和包体
 * @author Administrator
 *
 */
public class Packages {

	private Head head;//包头
	private Body body;//包体
	
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	
	public Packages(Head head, Body body) {
		super();
		this.head = head;
		this.body = body;
	}
	/**
	 * 返回包的字节数组
	 * @return
	 */
	public byte[] getPackage() {
		int headLenth = head.getBtye().length;
		int bodyLenth = 0;
		if (body != null) {
			bodyLenth = body.packet().length;
		}
		
		byte[] package_ = new byte[headLenth + bodyLenth];
		System.arraycopy(head.getBtye(), 0, package_, 0, headLenth);
		if (bodyLenth != 0) {
			System.arraycopy(body.packet(), 0, package_, headLenth, bodyLenth);
		}
		
		return package_;
		
	}
	
}
