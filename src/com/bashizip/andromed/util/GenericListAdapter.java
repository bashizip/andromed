package com.bashizip.andromed.util;

import java.util.List;






import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * @author Patrick Bashizi
 *
 * @param <T>
 */

public abstract class GenericListAdapter<T> extends BaseAdapter{

	
	List<T> list;
	LayoutInflater inflater;
	
	
	public  GenericListAdapter(Context context,List<T> list) {
		super();
		inflater = LayoutInflater.from(context);
		this.list=list;
	}
	

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return list.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	

}
