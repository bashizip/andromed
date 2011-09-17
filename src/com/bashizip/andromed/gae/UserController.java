package com.bashizip.andromed.gae;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.bashizip.andromed.data.User;

import android.util.Log;

public class UserController {
    public final ClientResource cr = new ClientResource(
	    EngineConfiguration.gae_path + "/rest/user");

    public UserController() {
	EngineConfiguration.getInstance();
    }

    public void create(User user) throws Exception {
	final UserControllerInterface uci = cr
		.wrap(UserControllerInterface.class);

	try {
	    uci.create(user);
	    Log.i("UserController", "Creation success !");
	} catch (Exception e) {
	    Log.i("UserController", "Creation failed !");
	    e.printStackTrace();
	    throw e;
	}
    }

    public List<User> getAllUsers() {
	final UserControllerInterface uci = cr
		.wrap(UserControllerInterface.class);
	Container<User> content = uci.getAllUsers();
	return content.getList();
    }
}
