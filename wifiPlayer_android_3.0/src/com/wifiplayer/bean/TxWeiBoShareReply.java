package com.wifiplayer.bean;

public class TxWeiBoShareReply {
//	{"data":{"id":"239844052946774","time":1366727812},"errcode":0,"msg":"ok","ret":0,"seqid":5870051255084649053}
	
	private Data data;
	private int errcode;
	private String msg;
	private int ret;
	private long seqid;
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public long getSeqid() {
		return seqid;
	}
	public void setSeqid(long seqid) {
		this.seqid = seqid;
	}
	
	
}
