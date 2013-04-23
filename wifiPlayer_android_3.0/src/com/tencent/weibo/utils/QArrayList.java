package com.tencent.weibo.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

/**
 * �޸���add(NameValuePair)�����ݸ�NameValuePair��valueֵ�Ƿ�Ϊ�գ������Ƿ�Ѹ�NameValuePair�Ž�QArrayList
 */
public class QArrayList extends ArrayList<NameValuePair> {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean add(NameValuePair nameValuePair) {
        if(QStrOperate.hasValue(nameValuePair.getValue())){
            return super.add(nameValuePair);
        }else{
            return false;
        }
    }
    
    


}
