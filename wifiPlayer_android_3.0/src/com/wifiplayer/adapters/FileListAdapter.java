package com.wifiplayer.adapters;

import java.util.List;

import com.wifiplayer.R;
import com.wifiplayer.bean.PcFile;

import android.content.Context;
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
	private List<PcFile> files;
	private int resource;
	private int[] ints;
	
	
	
	
	public FileListAdapter(Context context, List<PcFile> files, int resource,
			int[] ints) {
		super();
		this.context = context;
		this.files = files;
		this.resource = resource;
		this.ints = ints;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return files.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
		PcFile pf = files.get(position);
		nameTv.setText(pf.getName());
		size.setText(pf.getSize());
		date.setText("2013-04-14 11:11:12");
		return convertView;
	}

}
