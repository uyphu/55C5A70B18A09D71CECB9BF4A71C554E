package com.ltu.yealtube.utils;

import java.io.IOException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class RemoteApiExample {

	public static void main(String[] args) {
		RemoteApiOptions options = new RemoteApiOptions().server("yealtubecrontest.appspot.com", 443)
				.useApplicationDefaultCredential();
		RemoteApiInstaller installer = new RemoteApiInstaller();
		
		try {
			installer.install(options);
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			//System.out.println("Key of new entity is " + ds.put(new Entity("Hello Remote API!")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			installer.uninstall();
		}

	}

}
