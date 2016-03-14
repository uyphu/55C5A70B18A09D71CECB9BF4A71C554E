package com.ltu.yealtube.dao;

import java.io.InputStream;
import java.security.PrivateKey;

import com.google.api.client.util.SecurityUtils;
import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceConfig;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.remotely.RemoteCheck;
import com.googlecode.objectify.remotely.Remotely;
import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;


/**
 * A factory for creating RemoteObjectify objects.
 * @author uyphu
 */
public class RemoteObjectifyFactory extends ObjectifyFactory {

	/** The remotely. */
	private final Remotely remotely;

	/**
	 * Instantiates a new remote objectify factory.
	 */
	public RemoteObjectifyFactory() {
		register(Tube.class);
		register(Statistics.class);
		register(Report.class);

		RemoteApiOptions options = new RemoteApiOptions().server("yealtubetest.appspot.com", 443).useServiceAccountCredential(
				"yealtubetest@appspot.gserviceaccount.com", gePrivateKey());
		remotely = new Remotely(options, new RemoteCheck() {
			@Override
			public boolean isRemote(String namespace) {
				return true;
			}
		});
	}

	@Override
	protected AsyncDatastoreService createRawAsyncDatastoreService(DatastoreServiceConfig cfg) {
		return remotely.intercept(super.createRawAsyncDatastoreService(cfg));
	}
	
	/**
	 * Ge private key.
	 *
	 * @return the private key
	 */
	private PrivateKey gePrivateKey() {
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("yealtubetest-8eac9a49b0a0.p12");
			PrivateKey privateKey = SecurityUtils.loadPrivateKeyFromKeyStore(
				      SecurityUtils.getPkcs12KeyStore(), inputStream, "notasecret",
				      "privatekey", "notasecret");
			return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
