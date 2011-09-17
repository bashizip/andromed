package com.bashizip.andromed.datahelper;



import java.io.File;

import android.app.AlertDialog;
import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.ConfigScope;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.Db4oIOException;


public class DBTool {

	public static ObjectContainer db;
	private static DBTool instance = null;
	
	

	private DBTool() throws Exception {
		
		
		
		 EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
		 configuration.file().generateUUIDs(ConfigScope.GLOBALLY);
		 configuration.file().generateCommitTimestamps(true);
		
		try {
			
		
		
			db = Db4oEmbedded.openFile("/sdcard/andromedDB1.db4o");

			
		} catch (DatabaseFileLockedException e) {
			throw e;
		}catch(Db4oIOException e){
			throw e;
		}catch (Exception e) {
			throw e;
		}
	}

	public static DBTool getInstance() throws Exception {

		if (instance != null) {
			return instance;
		} else {

			instance = new DBTool();
		}
		return instance;
	}

	public void store(Object o) {
		db.store(o);
		close();
		instance = null;
	}

	public void close() {
		try {

		} finally {
			db.close();
		}
	}
	
}