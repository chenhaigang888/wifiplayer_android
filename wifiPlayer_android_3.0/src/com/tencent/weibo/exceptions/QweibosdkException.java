package com.tencent.weibo.exceptions;

/**
 * ���ڼ�¼��� QweiboSDK ���쳣��Ϣ
 */
public class QweibosdkException extends Exception {

    private static final long serialVersionUID = -1096752997048057364L;

    private String errcode;
    private String errmsg;

    /**
     * @param errcode
     * @param errmsg
     */
    public QweibosdkException(String errcode, String errmsg) {
        super();
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
