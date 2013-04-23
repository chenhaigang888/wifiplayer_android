package com.tencent.weibo.exceptions;

import com.tencent.weibo.constants.ErrorCodeConstants;

/**
 * ���ڼ�¼��� OAuthClient ���쳣��Ϣ
 */
public class OAuthClientException extends Exception {

    private static final long serialVersionUID = -89490187565897565L;
    
    private String errcode;
    private String errmsg;

    /**
     * ���ݴ��������Exception
     * @param errcode
     */
    public OAuthClientException(String errcode) {
        super(ErrorCodeConstants.getErrmsg(errcode));
        this.errcode = errcode;
        this.errmsg = ErrorCodeConstants.getErrmsg(errcode);
    }
    /**
     * ������ʹ�ã�ֻ����ʱ���ô�����
     * @param errcode
     * @param errmsg
     */
    public OAuthClientException(String errcode, String errmsg) {
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
