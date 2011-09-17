package com.bashizip.andromed;

import java.util.ArrayList;
import java.util.List;

import com.bashizip.andromed.R;
import com.bashizip.andromed.application.AndromedApplication;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.datahelper.DBTool;

import com.bashizip.andromed.util.PostLA;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PostList extends Activity {

	ListView lvPosts;
	List<Post> posts = new ArrayList<Post>();

	private DBTool dbcon;
	PostLA adapter;

	// Menu item ids
	public static final int MENU_ITEM_DELETE = Menu.FIRST;
	public static final int MENU_ITEM_INSERT = Menu.FIRST + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientslist);
		lvPosts = (ListView) findViewById(R.id.lv_patients);

		// app=(AndromedApplication) getApplicationContext();
		try {
			dbcon = DBTool.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadPosts();

		registerForContextMenu(lvPosts);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo info;

		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e("Creating option menu", "bad menuInfo", e);
			return;
		}

		Post p = dbcon.db.query(Post.class).get((int) info.position);

		menu.setHeaderTitle(p.getName());

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_list, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
	//	inflater.inflate(R.menu.menu_list, menu);

		/*
		 * menu.add(0, MENU_ITEM_INSERT, 0, R.string.add_Post)
		 * .setShortcut('3', 'a') .setIcon(android.R.drawable.ic_menu_add);
		 */
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		dbcon.close();
	}

	private void loadPosts() {

		posts = dbcon.db.queryByExample(Post.class);
		adapter = new PostLA(this, posts);
		lvPosts.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	public boolean onMenuItemSelected(int id, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.option_new_data:

			//startActivity(new Intent(this, AddPost.class));
			//finish();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(id, item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		// Delete the note that the context menu is for
		// Uri noteUri = ContentUris.withAppendedId(getIntent().getData(),
		// info.id);

		switch (item.getItemId()) {
		case R.id.option_display:
			displayPost(info.id);

			return true;
		case R.id.delete:
			deletePost(info.id);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void displayPost(long id) {
	
		Post p = dbcon.db.query(Post.class).get((int) id);

	}

	private void deletePost(long id) {

		Log.i("deletion", "selection: " + id);

		Post p = dbcon.db.query(Post.class).get((int) id);

		dbcon.db.delete(p);

		loadPosts();

		adapter.notifyDataSetChanged();

		Toast.makeText(this, "Supprimé: " + p.getName(), Toast.LENGTH_LONG)
				.show();

	}

}
