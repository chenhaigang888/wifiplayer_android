package com.wifiplayer.bean;


/**
 * 电脑上文件和目录
 * @author chenkaigang
 *
 */
public class PcFile {

	private String name;//文件（目录）的名称
	private String path;//当前文件（目录）的路径
	private String dir;//当前文件是否为文件夹
	private String size;//文件大小
	private String createDate;//文件创建日期
	private String sys;//是否为系统
	
	
	

	
	
	
	public PcFile() {
		super();
	}



	@Override
	public String toString() {
		return "PcFile [name=" + name + ", path=" + path + ", dir=" + dir
				+ ", size=" + size + ", createDate=" + createDate + "]";
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getDir() {
		return dir;
	}



	public void setDir(String dir) {
		this.dir = dir;
	}



	public String getSize() {
		return size;
	}



	public void setSize(String size) {
		this.size = size;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getSys() {
		return sys;
	}



	public void setSys(String sys) {
		this.sys = sys;
	}


	

	
	
	
}
