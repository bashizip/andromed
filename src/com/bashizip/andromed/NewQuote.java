package com.bashizip.andromed;

import java.util.Calendar;

import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.datahelper.DBTool;


import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewQuote extends Activity implements OnClickListener{

	private DBTool dbcon;
	private EditText editor;
	private Button btn_save_quote,btn_ann_quote;
	
	SharedPreferences preferences;  
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        
		   super.onCreate(savedInstanceState);
	       setContentView(R.layout.new_quote);
	        
	      editor = (EditText) findViewById(R.id.editor);
	      btn_save_quote=(Button) findViewById(R.id.btn_save_quote);
	      btn_ann_quote=(Button) findViewById(R.id.btn_ann_quote);
	      try {
			dbcon=DBTool.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      btn_save_quote.setOnClickListener(this);
	      btn_ann_quote.setOnClickListener(this);
	      preferences=PreferenceManager.getDefaultSharedPreferences(this);
	    }
	   
	   @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
			super.onActivityResult(requestCode, resultCode, data);
			dbcon.close();
			
		}
	   
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();		
			inflater.inflate(R.menu.menu_quotes, menu);
			return true;
		}

		@Override
		public boolean onMenuItemSelected(int id, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.option_save:
			if(editor.getText().length()>2){
				saveQuote();
			}else{
				Toast.makeText(this, "Rien à publier !",Toast.LENGTH_SHORT);
				break;
			}
				break;
			case R.id.option_discard:
				
				break;
			default:
				break;
			}
			return super.onMenuItemSelected(id, item);
		}
		
		

		private void saveQuote() {
			
			Quote quote=new Quote();
			
			quote.setText(editor.getText().toString());
			quote.setAuthor(preferences.getString("nom",""));
			dbcon.store(quote);
								
			Toast.makeText(this, "Publication enregistrée",Toast.LENGTH_LONG);
			
			Log.i("new quote", "Saved successfully !");
			startActivity(new Intent(this,QuotesActivity.class));
			finish();		
			
		}

		@Override
		public void onClick(View v) {
			if(v==btn_save_quote){
				saveQuote();
			}
			else if(v==btn_ann_quote){
				startActivity(new Intent(this,QuotesActivity.class));
				finish();		
			}
			
		}
		
		
	   
	   
}
