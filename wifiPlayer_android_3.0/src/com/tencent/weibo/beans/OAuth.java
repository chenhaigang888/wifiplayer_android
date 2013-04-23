package com.tencent.weibo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.constants.OAuthConstants;

/**
 * OAuth��Ȩ��Ϣͨ�ò���
 */
public class OAuth implements Serializable{

    private static final long serialVersionUID = -6939447877705891817L;
    protected String seqid=null;
    protected String clientIP="127.0.0.1";
    protected String appFrom=null;//androidӦ��Ӧ������Ϊ android-sdk-1.0
    protected String openid= null;//�û�ͳһ��ʶ
    protected String openkey= null;
    protected String oauthVersion = OAuthConstants.OAUTH_VERSION_1;// OAuth��Ȩ�İ汾 1.0 �� 2.a
    protected String scope = "all"; // ��Ȩ��Χ
    protected int status = 0;// ��֤״̬,0:�ɹ�,1:��ȡRequestʧ�� 2:��ȡ��Ȩ��ʧ��, 3:��ȡAccessʧ��
    protected String msg = null;//���������ص���Ϣ
    
    protected Random random = new Random();
    
    public List<NameValuePair> getCommonParamsList(){
        seqid=this.generateSeqId();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//        parameters.add(new BasicNameValuePair("seqid",  seqid));
           parameters.add(new BasicNameValuePair("clientip",clientIP));
//        parameters.add(new BasicNameValuePair("appfrom",appFrom));
//        parameters.add(new BasicNameValuePair("openid",  openid));
        return parameters;
    }
    
    /**
     * ����seqId��12λ�����
     * @return
     */
    public String generateSeqId(){
        String result="";
        for(int i=0;i<2;i++){
            result=String.valueOf(random.nextInt(1000000))+result;
            for(;result.length()<(i+1)*6;result="0"+result);
        }
        return result;
    }

    /**������*/
    public String getSeqId() {
        return seqid;
    }

    /**�����룬ʹ�� {@link #generateSeqId()} ���� */
    public void setSeqId(String seqId) {
        this.seqid = seqId;
    }

    /**�û���ʵIP��������ȡ��*/
    public String getClientIP() {
        return clientIP;
    }

    /**�������û���ʵIP������ʹ������IP��ȱʡֵΪ"127.0.0.1" */
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    /**Ӧ����ʹ�õ�SDK�汾*/
    public String getAppFrom() {
        return appFrom;
    }

    /**Ӧ����ʹ�õ�SDK�汾����� {@link com.tencent.weibo.constants.OAuthConstants#APP_FROM_ANDROID_SDK_1} �� 
     * {@link com.tencent.weibo.constants.OAuthConstants#APP_FROM_JAVA_SDK_2} ��ѡ��  */
    public void setAppFrom(String appFrom) {
        this.appFrom = appFrom;
    }

    /**�û���¼̬*/
    public String getOpenid() {
        return openid;
    }

    /**��ֵ���û���Ȩ������ȡ�ã�����û���¼̬*/
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**��openid��Ӧ��Key*/
    public String getOpenkey() {
        return openkey;
    }

    /**��ֵ���û���Ȩ��ȡ��,��openid��Ӧ*/
    public void setOpenkey(String openkey) {
        this.openkey = openkey;
    }

    /**ʹ�õ�OAuth��Ȩ�İ汾*/
    public String getOauthVersion() {
        return oauthVersion;
    }

    /**��� {@link com.tencent.weibo.constants.OAuthConstants#OAUTH_VERSION_1} ��
     *  {@link com.tencent.weibo.constants.OAuthConstants#OAUTH_VERSION_2_A} ��ѡ��  */
    public void setOauthVersion(String oauthVersion) {
        this.oauthVersion = oauthVersion;
    }

    /**��Ȩ��Χ */
    public String getScope() {
        return scope;
    }

    /**��Ȩ��Χ */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**��֤״̬:<br>
     * <li>0:�ɹ�,
     * <li>1:Requestʧ��,
     * <li>2:��ȡ��֤��ʧ��,
     * <li>3:Accessʧ��
     */
    public int getStatus() {
        return status;
    }

    /**��֤״̬:<br>
     * <li>0:�ɹ�,
     * <li>1:Requestʧ��,
     * <li>2:��ȡ��֤��ʧ��,
     * <li>3:Accessʧ��
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**���������ص���Ϣ*/
    public String getMsg() {
        return msg;
    }

    /**���������ص���Ϣ*/
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
