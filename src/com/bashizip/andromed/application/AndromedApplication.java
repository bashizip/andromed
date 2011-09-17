package com.bashizip.andromed.application;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class AndromedApplication extends Application {

	
	private ObjectContainer db;
		
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("App","Opening application");
		//db = Db4oEmbedded.openFile("/sdcard/andromedDB.db4o");	
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
	try{	// TODO Auto-generated method stub
	}finally{
		Log.i("App","Closing app");
			//db.close();
		}
		
	}
	
	
	
}
