package com.wifiplayer.utils;

public class Tools {

	double fileD;
	String[] strKB = {"B","KB","MB","GB","TB","PB"};
	int second = 0;//计算单位换算了多少次
	public String js(double fileLenth) {
		if (fileLenth >= 1024) {
			fileD = fileLenth/ 1024;
			second ++;
			js(fileD);
		}
		String fileSize = String.format("%.2f", fileD) + strKB[second];
		
		return fileSize;
		
	}
}
