package com.wifiplayer.bean;

import java.io.Serializable;

/**
 * 请求返回操作对象
 * @author Administrator
 *
 */
public class ReqReplyOp implements Serializable{

	private short cmd;//当前操作
	private int status;//状态 0表示成功，1表示操作失败
	private String content;//内容
	
	
	public short getCmd() {
		return cmd;
	}
	public void setCmd(short cmd) {
		this.cmd = cmd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
