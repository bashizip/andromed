

package com.bashizip.andromed;



import java.util.List;

import com.bashizip.andromed.R;

import com.bashizip.andromed.data.Desease;
import com.bashizip.andromed.data.Quote;

import com.bashizip.andromed.datahelper.DBTool;
import com.bashizip.andromed.util.Defaults;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ProgressBar;

public class Splash extends Activity {


	

    
    public boolean mExternalStorageAvailable = false;
	public boolean mExternalStorageWriteable = false;
	
 	 DBTool dbt;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
       
        
        checkStorageState();
        
        
        if(mExternalStorageAvailable|mExternalStorageWriteable){
        
			try {
				dbt=DBTool.getInstance();
				
				checkForFirstRun();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
        }else{
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("Oups !");
			builder.setMessage("SDCard absente ou indisponible,l'application ne fonctionnera pas correctement!");
			AlertDialog d=builder.create();
		
			d.show();
        }
    
        
        
     
        // setup handler to close the splash screen
        Handler x = new Handler();
        x.postDelayed(new Splashhandler(), 7000);
    }

    class Splashhandler implements Runnable {

        public void run() {

            // start new activity
          startActivity(new Intent(getApplication(),Andromed.class));
            // close out this activity 
            finish();

        }
    }
    
    
	
	
	void checkStorageState(){
		
	
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}
    
    
    
    private void checkForFirstRun() {
    	
   
				
		final List<Quote> quotes=dbt.db.queryByExample(Quote.class);
		
	
		
		final List<Desease> deseases=dbt.db.queryByExample(Desease.class);
	
			//pdial = ProgressDialog.show(this, "Processing...","Initializing facts...", true, true);		
		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if(quotes.isEmpty()){		
					for(int i=0; i<Defaults.defaultFactsString.length;i++){
						
						Quote q=new Quote();
						q.setText(Defaults.defaultFactsString[i]);
						
						q.setAuthor("OMS");
						
						dbt.db.store(q);
						
						
						Log.i("InitData", "new fact"+q.getText().substring(0, 10)+"...");
					}
					dbt.db.commit();
					  //dbt.close();
				//	pdial.dismiss();
					}
					
					
					if(deseases.isEmpty()){
						for(int i=0; i<Defaults.defaultDesases.length;i++){
							
							Desease d=new Desease();
							d.setName(Defaults.defaultDesases[i]);							
							
						
							dbt.db.store(d);
							
						}
						dbt.db.commit();
					}
					
				}
				
			}).start();
			
				
			
			
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
dbt.close();
		super.onActivityResult(requestCode, resultCode, data);
	}

	
}
