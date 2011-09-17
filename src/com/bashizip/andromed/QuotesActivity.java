package com.bashizip.andromed;

import java.util.ArrayList;
import java.util.List;

import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.ObjectSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.TextView;

public class QuotesActivity extends Activity {

	private DBTool dbcon;
	private TextView tv_quote, tv_from, tv_current;
	//private Button btn_next, btn_prev;
	private static int QUOTE_ID = 0;

	private List<Quote> quotes;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_quotes);

		tv_quote = (TextView) findViewById(R.id.edit_quote);
		tv_from = (TextView) findViewById(R.id.tv_from);
		tv_current = (TextView) findViewById(R.id.tv_curr_id);
		/*btn_next = (Button) findViewById(R.id.btn_next);
		btn_prev = (Button) findViewById(R.id.btn_prev);*/

		try {
			dbcon = DBTool.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadQuotes();

		final GestureDetector gd = new GestureDetector(new MyGestureListner());

		tv_quote.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (gd.onTouchEvent(event)) {
					return true;
				} else
					return false;
			}
		});

//		btn_next.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				displayNextQuote();
//				btn_prev.setEnabled(true);
//			}
//		});
//
//		btn_prev.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				displayPreviousQuote();
//				btn_next.setEnabled(true);
//			}
//		});
		if (quotes.size() == 0) {
			loadDefaultData();
		}
		displayQuote(quotes.size() - 1);
	}

	private void loadDefaultData() {
		// TODO Auto-generated method stub

	}

	private void loadQuotes() {

		ObjectSet<Quote> all = dbcon.db.queryByExample(Quote.class);

		quotes = new ArrayList<Quote>(all);

	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_fact, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int id, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.option_add_fact:

			startActivity(new Intent(this, NewQuote.class));
			finish();
			break;
		case R.id.option_share_fact:

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");

			intent.putExtra(Intent.EXTRA_SUBJECT, "Malaria");
			intent.putExtra(Intent.EXTRA_TEXT, tv_quote.getText().toString()
					+ " --  Andromed");

			Intent chooser = Intent.createChooser(intent, "Andomed");
			startActivity(chooser);

			break;
	    	case R.id.option_goto:
			
			int i = prompt("Goto ID...", this);

			displayQuote(i);

			break;
		default:
			break;
		}
		return super.onMenuItemSelected(id, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		dbcon.close();

	}

	private void displayNextQuote() {

		Log.i("Display quotes", "Size:" + quotes.size());
		
		if (QUOTE_ID < quotes.size()) {
			QUOTE_ID++;
			displayQuote(QUOTE_ID);

		} else {
			tv_quote.setText("Say no to Malaria by Sharing or Adding  a fact !");
			/*btn_next.setEnabled(false);
			btn_prev.setEnabled(true);*/
		}
	}

	private void displayPreviousQuote() {

		if (QUOTE_ID > 0) {

			displayQuote(QUOTE_ID - 1);
			QUOTE_ID--;

		} else {
			tv_quote.setText("Start");
			/*btn_next.setEnabled(true);
			btn_prev.setEnabled(false);*/
			return;
		}
	}

	// -------------------------------------------------------------------------------------
	private void displayQuote(int id) {

		if (id < 0) {
			Toast.makeText(getApplicationContext(), "Start",
					Toast.LENGTH_LONG).show();
			return;
		}
		
		Quote quote = null;
		
		if (id >= quotes.size()) {
			return;
			
		} else {
			quote = quotes.get(id);
		
		tv_quote.setText(quote.getText());
		tv_current.setText(id + "/" + quotes.size());

		String firstname ;
		
		if(quote.getAuthor()!=null){
			firstname=quote.getAuthor();
		}else{
			firstname="Inconnu";
		}
		
			tv_from.setText("From : "+firstname);
		}
	}

	public int prompt(String message, Context ctx) {
		// load some kind of a view
		LayoutInflater li = LayoutInflater.from(ctx);
		View view = li.inflate(R.layout.prompt, null);
		// get a builder and set the view
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Prompt");
		builder.setView(view);
		// add buttons and listener

		PromptListener pl = new PromptListener(view);

		builder.setPositiveButton("OK", pl);
		builder.setNegativeButton("Annuler", pl);
		// get the dialog
		AlertDialog ad = builder.create();
		// show
		ad.show();
		return pl.getPromptReply();
	}

	class PromptListener implements
			android.content.DialogInterface.OnClickListener {
		// local variable to return the prompt reply value
		private int promptReply = 1;
		// Keep a variable for the view to retrieve the prompt value
		View promptDialogView = null;

		// Take in the view in the constructor
		public PromptListener(View inDialogView) {
			promptDialogView = inDialogView;
		}

		// Call back method from dialogs
		public void onClick(DialogInterface v, int buttonId) {
			if (buttonId == DialogInterface.BUTTON1) {
				// ok button
				promptReply = getPromptText();
			} else {
				// cancel button
				promptReply = 1;
			}
		}

		// Just an access method for what is in the edit box
		private int getPromptText() {
			EditText et = (EditText) promptDialogView
					.findViewById(R.id.promptEditTextControlId);

			int out = 1;
			try {
				out = Integer.valueOf(et.getText().toString());
			} catch (NumberFormatException e) {
				Toast.makeText(getApplicationContext(), "Bad input",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Bad input",
						Toast.LENGTH_SHORT).show();
			}
			return out;
		}

		public int getPromptReply() {
			return promptReply;
		}
	}

	class MyGestureListner extends SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

				displayNextQuote();

			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				displayPreviousQuote();
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// copy to clipbord
			super.onLongPress(e);
		}

	}

}
