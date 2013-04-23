
package com.wifiplayer.adapters;

import java.util.List;

import com.wifiplayer.R;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.bean.FindedPC;
import com.wifiplayer.net.udp.SearchPc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 可以控制的pc列表Adapter
 * @author Administrator
 *
 */
public class EnableCtrlPCAdapter extends BaseAdapter {

	private Context context;
	List<FindedPC> pcs;
	int[] ints;
	int resource;
	
	
	

	public EnableCtrlPCAdapter(Context context, List<FindedPC> pcs,
			int[] ints, int resource) {
		super();
		this.context = context;
		this.pcs = pcs;
		this.ints = ints;
		this.resource = resource;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pcs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pcs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		TextView name = (TextView) view.findViewById(ints[0]);
		TextView osVision = (TextView) view.findViewById(ints[1]);
		TextView addr = (TextView) view.findViewById(ints[2]);
		ImageView pcImgImageView = (ImageView) view.findViewById(ints[3]);
		
		FindedPC fPC = pcs.get(arg0);
		if (fPC.getOsName().equals("android")) {
			pcImgImageView.setImageResource(R.drawable.android);
		} else if (fPC.getOsName().equals("ios")) {
			pcImgImageView.setImageResource(R.drawable.ios);
		} else if (fPC.getOsName().equals("Mac OS X")) {
			pcImgImageView.setImageResource(R.drawable.mac);
		} else {
			pcImgImageView.setImageResource(R.drawable.win);
		}
		
		name.setText(fPC.getPcUser());//电脑名称
		osVision.setText(fPC.getOsName() + "_" + fPC.getVersion());
		addr.setText(fPC.getDatagramPacket().getAddress() + "");
		
		return view;
	}

}
