package com.bashizip.andromed.gae;



import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.data.Post;
 
	public interface PostControllerInterface {
	 @Put
	 void create(Post patient);
	 
	 @Get
	 Container getAllPatients();
	 
	}

