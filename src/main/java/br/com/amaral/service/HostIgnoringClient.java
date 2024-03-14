/**
 * This class initializes a Jersey client with the ability to ignore SSL certificate hostname verification 
 * by configuring a custom TrustManager and HostnameVerifier. The client also supports JSON serialization 
 * using JacksonJsonProvider and multipart requests using MultiPartWriter.
 * 
 * Note: Intended for API testing in a SandBox environment
 */
package br.com.amaral.service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;


public class HostIgnoringClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hostName;

	public HostIgnoringClient(String hostName) {
		this.hostName = hostName;
	}

	public Client hostIgnoringClient() throws Exception {

		TrustManager[] trustManagers = new TrustManager[] {

				new X509TrustManager() {

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					@Override
					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

					}

					@Override
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

					}
				} };

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustManagers, new SecureRandom());

		Set<String> hostNameList = new HashSet<>();
		hostNameList.add(this.hostName);
		HttpsURLConnection.setDefaultHostnameVerifier(new IgnoreHostNameSSL(hostNameList));

		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

		DefaultClientConfig config = new DefaultClientConfig();
		Map<String, Object> properties = config.getProperties();
		HTTPSProperties httpsProperties = new HTTPSProperties(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		}, sslContext);

		properties.put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties);
		config.getClasses().add(JacksonJsonProvider.class);
		config.getClasses().add(MultiPartWriter.class);

		return Client.create(config);

	}

}
