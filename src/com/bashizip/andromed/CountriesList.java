package com.bashizip.andromed;

import com.bashizip.andromed.datahelper.DBTool;
import com.bashizip.andromed.util.CountriesLA;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CountriesList extends Activity {

	
ListView lv_countries;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.countrieslist);
		
		lv_countries = (ListView) findViewById(R.id.lv_pays);

		// app=(AndromedApplication) getApplicationContext();
		try {
			DBTool dbcon = DBTool.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       lv_countries.setAdapter(new CountriesLA(this));


	}

	
	
	
	
}





