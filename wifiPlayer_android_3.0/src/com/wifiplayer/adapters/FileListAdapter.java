package com.wifiplayer.adapters;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wifiplayer.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 文件列表的适配器
 * @author chenkaigang
 *
 */
public class FileListAdapter extends BaseAdapter {

	private Context context;
	private JSONArray files;
	private int resource;
	private int[] ints;
	
	
	
	
	public FileListAdapter(Context context, JSONArray files, int resource,
			int[] ints) {
		super();
		this.context = context;
		this.files = files;
		this.resource = resource;
		this.ints = ints;
	}

	@Override
	public int getCount() {
		return files.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return files.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(resource, null);
		ImageView iv = (ImageView) convertView.findViewById(ints[0]);
		TextView nameTv = (TextView) convertView.findViewById(ints[1]);
		TextView size = (TextView) convertView.findViewById(ints[2]);
		TextView date = (TextView) convertView.findViewById(ints[3]);
		
		iv.setImageResource(R.drawable.ic_launcher);
		
		JSONObject pf;
		
		try {
			pf = (JSONObject) files.get(position);
			nameTv.setText(pf.getString("name"));
			size.setText(pf.getString("size"));
			if (position == 0 && !pf.getBoolean("sys")) {
				iv.setImageResource(R.drawable.pre);
			} else {
				if (pf.getBoolean("dir")) {
					iv.setImageResource(R.drawable.dir);
				} else {
					String suffix = getFileSuffix(pf.getString("path"));//文件后缀
					if (suffix.toLowerCase().equals("mp3") || suffix.toLowerCase().equals("wma") || suffix.toLowerCase().equals("wav") || suffix.toLowerCase().equals("ape") || suffix.toLowerCase().equals("flac") || suffix.toLowerCase().equals("aac") || suffix.toLowerCase().equals("m4a") ) {//音乐文件
						//音乐文件
						iv.setImageResource(R.drawable.music);
					} else if (suffix.toLowerCase().equals("mp4") || suffix.toLowerCase().equals("wmv") || suffix.toLowerCase().equals("rm") || suffix.toLowerCase().equals("rmvb") || suffix.toLowerCase().equals("mkv") || suffix.toLowerCase().equals("tp") || suffix.toLowerCase().equals("ts") || suffix.toLowerCase().equals("mov") || suffix.toLowerCase().equals("3gp") || suffix.toLowerCase().equals("avi")) {
						//视频文件
						iv.setImageResource(R.drawable.video);
					} else if(suffix.toLowerCase().equals("zip")) {
						//压缩文件
						iv.setImageResource(R.drawable.zip);
					} else if (suffix.toLowerCase().equals("png") || suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("jpeg") || suffix.toLowerCase().equals("gif") || suffix.toLowerCase().equals("bmp")) {
						iv.setImageResource(R.drawable.pic);
					} else {
						iv.setImageResource(R.drawable.ask);
					}
					
				}
				date.setText(pf.getString("createDate"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	/**
	 * 获取文件的后罪名
	 * @param path
	 */
	public String getFileSuffix(String path) {
		return path.substring(path.lastIndexOf(".") + 1, path.length());
	}

}
