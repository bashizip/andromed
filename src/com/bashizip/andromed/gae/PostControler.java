package com.bashizip.andromed.gae;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.data.Post;

import android.util.Log;

public class PostControler {

	public final ClientResource cr = new ClientResource(
			EngineConfiguration.gae_path + "/rest/post");

	public PostControler() {
		EngineConfiguration.getInstance();
	}

	public void create(Post user) throws Exception {
		final PostControllerInterface uci = cr
				.wrap(PostControllerInterface.class);

		try {
			uci.create(user);

			Log.i("PatientController", "Creation success !");

		} catch (Exception e) {
			Log.e("PatientController", e.getMessage());
			Log.i("PatientController", "Creation failed !");
			e.printStackTrace();
			throw e;
		}
	}

	public List getAllPatients() {
		final PostControllerInterface uci = cr
				.wrap(PostControllerInterface.class);
		Container<Patient> content = uci.getAllPatients();
		return content.getList();
	}
}
