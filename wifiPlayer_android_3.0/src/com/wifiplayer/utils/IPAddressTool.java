package com.wifiplayer.utils;

import java.util.regex.Pattern;

public class IPAddressTool {

	/**
	 * 判断ip是否合法
	 * @param str
	 * @return
	 */
	public static boolean isIPAdress( String str ){  
	    Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );  
	    return pattern.matcher( str ).matches();  
	}
	 
	 
}
