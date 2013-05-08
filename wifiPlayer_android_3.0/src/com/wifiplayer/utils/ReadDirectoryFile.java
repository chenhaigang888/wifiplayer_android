package com.wifiplayer.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.wifiplayer.bean.PcFile;


public class ReadDirectoryFile {
//	ArrayList<HashMap<String,Object>> lstPaths = new ArrayList<HashMap<String,Object>>();
	/**
	 * 传一个文件夹路径返回当前文件夹下所有的文件
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String listFile(String path){
		ReadDirectoryFile rdf = new ReadDirectoryFile();
		List<PcFile> list = new ArrayList<PcFile>();
		List<PcFile> dirs = new ArrayList<PcFile>();
		List<PcFile> files = new ArrayList<PcFile>();
		File[] fs = null; 
		System.err.println("查看目录:"+path);
		File file = new File(path);
		if(file==null){
			return null;
		}else if(file.isDirectory()){
			fs = file.listFiles();
			if(fs==null){
				return null;
			}else{
				for(File f:fs){
					PcFile pf = new PcFile();
					pf.setName(f.getName());
					pf.setPath(f.getAbsolutePath());
					pf.setCreateDate(getFileModifyTime(f));
					pf.setSys("false");
					if(f.isDirectory()){//设置是否为文件夹
						pf.setSize("");
						pf.setDir("true");
						dirs.add(pf);
					}else{
						pf.setSize(new Tools().js(f.length()));
						pf.setDir("false");
						files.add(pf);
					}
					
					
				}
			}
		}
		PcFile pf = new PcFile();
		pf.setName("\\上一页...");
		pf.setSys("false");
		pf.setCreateDate("");
		pf.setDir("false");
		pf.setPath("");
		pf.setSize("");
		list.add(pf);
		/*将文件排序*/
		for(int i=0; i<dirs.size(); i++){
			list.add(dirs.get(i));
		}
		for(int i=0; i<files.size(); i++){
			list.add(files.get(i));
		}
		
		Gson g = new Gson();
		String str = g.toJson(list);
		return str;
	}
	
	/**
	 * 获取文件的最后修改时间
	 * @param file
	 * @return
	 */
	private static String getFileModifyTime(File file) {
		 long time = file.lastModified();//返回文件最后修改时间，是以个long型毫秒数
		 String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(time));
		return ctime;
	}
	
	/**
	 * 读取系统分区
	 * @return
	 */
	public static String systemRoots() {
//		List<PcFile> list = new ArrayList<PcFile>();
//		PcFile pf = null;
//		File[] roots = File.listRoots();
//		for (int i=0; i<roots.length; i++) {
//			pf = new PcFile();
//			pf.setName(roots[i].toString());
//			String str = (new Tools().js(roots[i].getFreeSpace()));
//			pf.setSize(str + "可用，共" + new Tools().js(roots[i].getTotalSpace()));
//			pf.setSys(true);
//			list.add(pf);
//		}
//		JSONArray jsonArray = JSONArray.fromObject(list);
		
		
		return listFile("/mnt/sdcard/");
		
	}
	
	/**
	 * 删除文件，包括文件夹
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file){
		System.err.println("删除文件");
		if (!file.isDirectory()) {//如果不是文件夹
			file.delete();
			return true;
		}
		File[] files = file.listFiles();
		try {
			for(int i=0; i<files.length; i++){
				File f = files[i];
				if(f.delete()){
					System.out.println(f.getAbsolutePath());
				}else{
					delFile(f);
				}
			}
			file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	
}
