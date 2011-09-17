package com.bashizip.andromed;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bashizip.andromed.R;
import com.bashizip.andromed.application.AndromedApplication;
import com.bashizip.andromed.data.Category;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.data.Status;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.config.TVector;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPost extends Activity implements OnClickListener {

	// private DataBaseConnection dbcon;
	SharedPreferences preferences;

	EditText tf_nom, tv_age,
	         tv_illness_days;

	CheckBox cb_underTreatement;

	Spinner spin_cat;
	
	Button btn_save, btn_annuler;
	

	// AndromedApplication app;

	private Post post;
	private DBTool dbcon;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpost);

		// app=(AndromedApplication) getApplicationContext();

		try {
			dbcon = DBTool.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// btn_pic=(Button) findViewById(R.id.btn_pic);

		tf_nom = (EditText) findViewById(R.id.tv_name);
		tv_age = (EditText) findViewById(R.id.tv_age);
		tv_illness_days = (EditText) findViewById(R.id.tv_days);
		
		btn_save=(Button)findViewById(R.id.btn_save_new_post);
		btn_annuler=(Button)findViewById(R.id.btn_annuler_post);
		
		
		spin_cat = (Spinner) findViewById(R.id.spiner_categorie);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, new String[] { "",
						Category.ADULTE_M, Category.ADULTE_W, Category.ENFANT,
						Category.FEMME_ENCEINTE });
		spin_cat.setAdapter(aa);

		cb_underTreatement = (CheckBox) findViewById(R.id.cb_underTreatement);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	
		btn_save.setOnClickListener(this);
		btn_annuler.setOnClickListener(this);
	}

	private void createpostObject() {

		post = new Post();

		try {
			post.setAge(Integer.valueOf(tv_age.getText().toString()));
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(),
					"Error: 'Age'  not a number", Toast.LENGTH_SHORT).show();
			return;
		}
		post.setName(tf_nom.getText().toString());
		try {
			post.setSinceDays(Integer.valueOf(tv_illness_days.getText()
					.toString()));
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(),
					"Error: 'days' not a number", Toast.LENGTH_SHORT).show();
			return;
		}

		post.setCategorie(spin_cat.getSelectedItem().toString());
		post.setUnderTreatment(cb_underTreatement.isChecked()?true:false);
		post.setPostDate(new Date() + "");

		post.setFirstname(preferences.getString("name", ""));

		boolean sendMyMail = preferences.getBoolean("send_my_mail", true);
		boolean sendMyNum = preferences.getBoolean("send_my_num", true);

		post.setMail(sendMyMail ? preferences.getString("mail", "") : "unkwown");
		post.setPhone(sendMyNum ? getMyPhoneNumber() : "not set");

		post.setZone(preferences.getString("zone", ""));
		post.setPays(preferences.getString("list_pays", ""));
		post.setDesease(preferences.getString("list_maladie", ""));

		dbcon.store(post);
		
		Toast.makeText(getApplicationContext(), "Donnée crée avec succes !\n\n" +
				"Tel:"+post.getPhone()+"\n"	+	
				"E-mail:" +post.getMail()+"\n"+
				"Zone :"+post.getZone()+"\n"+
				"Pays:"+post.getPays()+"\n"+
				"Maladie:"+post.getDesease()+"\n"
,
				
				Toast.LENGTH_LONG).show();
		
		startActivity(new Intent(this, PostList.class));

		
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	//	inflater.inflate(R.menu.menu_new_patient, menu);
		
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		dbcon.close();

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.option_save:

			createpostObject();

			break;
		case R.id.option_discard:
			cleanFields();
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	void cleanFields() {

	}

	private String getMyPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager)
		getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	private String getMy10DigitPhoneNumber() {
		String s = getMyPhoneNumber();
		return s.substring(2);
	}

	@Override
	public void onClick(View v) {
		if(v==btn_save){
			createpostObject();
		}else if(v==btn_annuler){
			startActivity(new Intent(this, Andromed.class));
		}
	}

}
