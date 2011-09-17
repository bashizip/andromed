package com.bashizip.andromed;

import java.util.List;

import com.bashizip.andromed.data.*;
import com.bashizip.andromed.gae.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.query.Predicate;

public class SyncCenterActivity extends Activity implements OnClickListener {

	private Button btn_m2c;
	private ProgressDialog pDial;
	private final PostControler pCtrl = new PostControler();
	TextView tv_sync_count;

	// private final UserController uCtrl = new UserController();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sync);

		// btn_m2d = (Button) findViewById(R.id.btn_sync_desk);
		btn_m2c = (Button) findViewById(R.id.btn_sync_cloud);

		tv_sync_count = (TextView) findViewById(R.id.tv_sync_count);

		refresh();

		// btn_m2c.setOnClickListener(this);
		btn_m2c.setOnClickListener(this);

	}

	void refresh() {
		int unsync = getAllUnsyncPosts().size();

		tv_sync_count.setText(unsync+"");
	}

	private boolean haveInternet() {
		NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// disable internet while roaming, just return false
			return true;
		}
		return true;
	}

	List<Post> getAllUnsyncPosts() {
		List<Post> posts;
		try {
			posts = DBTool.db.query(new Predicate<Post>() {

				public boolean match(Post o) {
					return o.getSyncStatus() == 0;
				}
			});

		} finally {
			// DBTool.db.close();
		}
		return posts;
	}

	void processData() {

		final ProgressDialog dialog = ProgressDialog.show(this, "Réplication",
				"Envoi de données", true);
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				dialog.dismiss();
			}
		};
		Thread Update = new Thread() {
			public void run() {
				//
				// YOUR LONG CALCULATION (OR OTHER) GOES HERE
				//
				for (Post p : getAllUnsyncPosts()) {

					// pDial.setMessage("sending data"+u.toString());

					try {
						// handler.sendMessage(new Message());
						pCtrl.create(p);

						p.setSyncStatus(1);
						DBTool.db.store(p);
						DBTool.db.commit();
						// pDial.setMessage("Finished");
					} catch (Exception e) {
						dialog.dismiss();
						break;
					}

					handler.sendEmptyMessage(1);
				}
				
				
			}
		};
		Update.start();

		try {
			Update.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.dismiss();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == btn_m2c) {
			if (haveInternet()) {
				processData();
				
			} else {
				return;
			}
		}
	}

}
