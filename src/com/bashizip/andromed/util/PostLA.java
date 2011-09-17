package com.bashizip.andromed.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bashizip.andromed.R;
import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.data.Post;


public class PostLA extends GenericListAdapter<Post>{

	ViewHolder	holder;
	
	public PostLA(Context context, List<Post> list) {
		super(context, list);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
		holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.itempatient, null);
			holder.tvNom = (TextView)convertView.findViewById(R.id.tv_nom);
			holder.tvSexe = (TextView)convertView.findViewById(R.id.tv_status);
			holder.imageView=(ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder); 
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvNom.setText(list.get(position).getName());
		holder.tvSexe.setText("Age:"+list.get(position).getAge()+"   Sync status:"+list.get(position).getSyncStatus());
		return convertView;
	}
	
	private class ViewHolder {
		public TextView tvNom;
		TextView tvSexe;
		ImageView imageView;
	}

}
