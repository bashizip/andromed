package com.bashizip.andromed;



import java.util.Vector;

import com.bashizip.andromed.R;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Andromed extends Activity implements OnClickListener {
	
	private static int CODE_RETOUR = 1;
	SharedPreferences preferences; 
	
	Button btnInvestigation, quoteList, btn_sync, btn_stored_cases,
			btn_settings;

	private Dialog aboutDialog;
	

	public static final int MENU_ITEM_WEB = Menu.FIRST;
	public static final int MENU_ITEM_ABOUT = Menu.FIRST + 1;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// Boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 setContentView(R.layout.main);

		
	/*	 if (customTitleSupported) {
				            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
				            TextView tv = (TextView) findViewById(R.id.tv_titre);
				            tv.setText("Andromed");
		 }
			*/	            
		btnInvestigation = (Button) findViewById(R.id.btn_investigation);
		btnInvestigation.setOnClickListener(this);
		// btnOK = (Button) findViewById(R.id.btn_ok);

		quoteList = (Button) findViewById(R.id.btn_all_quotes);
		quoteList.setOnClickListener(this);

		btn_sync = (Button) findViewById(R.id.btn_sync_center);
		btn_sync.setOnClickListener(this);

		btn_stored_cases = (Button) findViewById(R.id.btn_stored_cases);
		btn_stored_cases.setOnClickListener(this);

		
		btn_settings= (Button) findViewById(R.id.btn_settings);
		btn_settings.setOnClickListener(this);
		preferences=PreferenceManager.getDefaultSharedPreferences(this);

	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		if(requestCode==CODE_RETOUR){
		Toast.makeText(this, "Vos paramètres ont changés", Toast.LENGTH_SHORT).show();
		
	}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, MENU_ITEM_WEB, 0, R.string.web).setShortcut('1', 'a')
				.setIcon(R.drawable.ic_menu_globe);

		menu.add(0, MENU_ITEM_ABOUT, 0, R.string.about).setShortcut('1', 'a')
				.setIcon(android.R.drawable.ic_dialog_info);

		return true;
	}

	void showDialog() {
		
		aboutDialog = new Dialog(this);
		aboutDialog.setTitle("A propos de Andromed Tropical");

		aboutDialog.setCancelable(true);
		aboutDialog.setCanceledOnTouchOutside(true);
		aboutDialog.setContentView(R.layout.about);
		aboutDialog.show();
	}

	@Override
	public boolean onMenuItemSelected(int id, MenuItem item) {

		switch (item.getItemId()) {
		case MENU_ITEM_ABOUT:
			showDialog();
			break;
		case MENU_ITEM_WEB:
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://andromed-cloud.appspot.com"));
					startActivity(intent);
					break;
		default:
			break;
		}
		return super.onMenuItemSelected(id, item);
	}

	
	void openInvestigation(){

	String nom=preferences.getString("name", "");
	String zone=preferences.getString("zone", "");
	String pays=preferences.getString("list_pays", "");
	String maladie=preferences.getString("list_maladie", "");
	
	final Vector<String> notSet=new Vector<String>();
	

	if(pays==null|pays==""){
		notSet.add("pays");
	}
	if(zone==""|zone==null){
		notSet.add("zone");
	}
	if(nom.equals("")|nom==null){
		notSet.add("nom");
	}
	if(maladie==""|maladie==null){
		notSet.add("maladie");
	}
	
	if(notSet.size()!=0){
				
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	String msg="Vous devez d'abord  préciser les informations suivantes: ";
		for(String s:notSet){
			msg=msg+s+",";
		}
		msg+=" dans les paramètres.Cliquer sur Oui pour les fournir ";
		
		builder.setMessage(msg)
		
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   notSet.clear();
		        	 startActivityForResult(new Intent(  Andromed.this, Settings.class), CODE_RETOUR);        
		           }
		       })
		       .setNegativeButton("Non", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	
		        	   return; 
		          
		           }
		       });
		AlertDialog alert = builder.create(); 
		
	alert.show();
	}else  startActivity(new Intent( Andromed.this, AddPost.class)); 
	}

	@Override
	public void onClick(View v) {

		if (v == btnInvestigation) {
					
			openInvestigation();
			
		} else if (v == quoteList) {
			startActivity(new Intent(this, QuotesActivity.class));

		} else if (v == btn_sync) {
			startActivity(new Intent(this, SyncCenterActivity.class));
		} else if (v == btn_settings) {
			
			startActivityForResult(new Intent(this, Settings.class), CODE_RETOUR);
			
		} else if (v == btn_stored_cases) {
			startActivity(new Intent(this, PostList.class));
		}

	}

}