package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RestResourceTestHelper {

	private HttpClient client;

	public HttpClient getClient() {
		if (client == null) {
			client = new DefaultHttpClient();
		}
		return client;
	}

	public String getBaseUrl() {
		return "http://localhost:8080/resources";
	}

	public InputStream getContentStream(final String path,
			final String mediaType) throws IOException, ClientProtocolException {
		final HttpResponse rsp = getResponse(path, mediaType);
		return rsp.getEntity().getContent();
	}

	public JSONArray getPayloadArrayFromJson(final String path)
			throws ClientProtocolException, IOException, JSONException {
		final InputStream content = getContentStream(path, APPLICATION_JSON);
		return new JSONArray(new JSONTokener(new InputStreamReader(content)));
	}

	public JSONObject getPayloadFromJson(final String path)
			throws ClientProtocolException, IOException, JSONException {
		final InputStream content = getContentStream(path, APPLICATION_JSON);
		return new JSONObject(new JSONTokener(new InputStreamReader(content)));
	}

	public <T> T getPayloadFromXml(final String path,
			final Class<T> expectedClass) throws JAXBException, IOException,
			ClientProtocolException, FactoryConfigurationError,
			XMLStreamException {
		final XMLInputFactory f = XMLInputFactory.newFactory();
		final XMLStreamReader reader = f
				.createXMLStreamReader(getContentStream(path, APPLICATION_XML));
		final JAXBContext ctx = JAXBContext.newInstance(expectedClass);
		final Unmarshaller um = ctx.createUnmarshaller();
		final JAXBElement<T> el = um.unmarshal(reader, expectedClass);
		return el.getValue();
	}

	public <T> T postXmlPayloadAndGetXmlPayload(final String path,
			final String payload, final Class<T> expectedClass)
			throws ClientProtocolException, IOException, XMLStreamException,
			JAXBException {
		final HttpPost post = new HttpPost(getBaseUrl() + path);
		post.addHeader("Accept", APPLICATION_XML);
		post.setEntity(new StringEntity(payload, APPLICATION_XML, "UTF-8"));
		final HttpResponse rsp = getClient().execute(post);
		final InputStream is = rsp.getEntity().getContent();
		final XMLInputFactory f = XMLInputFactory.newFactory();
		final XMLStreamReader reader = f.createXMLStreamReader(is);
		final JAXBContext ctx = JAXBContext.newInstance(expectedClass);
		final Unmarshaller um = ctx.createUnmarshaller();
		final JAXBElement<T> el = um.unmarshal(reader, expectedClass);
		return el.getValue();
	}

	public JSONObject postJsonPayloadAndGetJsonPayload(final String path,
			final String payload) throws ClientProtocolException, IOException,
			JSONException {
		final HttpPost post = new HttpPost(getBaseUrl() + path);
		post.addHeader("Accept", APPLICATION_JSON);
		post.setEntity(new StringEntity(payload, APPLICATION_JSON, "UTF-8"));
		final HttpResponse rsp = getClient().execute(post);
		final InputStream is = rsp.getEntity().getContent();
		final String content = IOUtils.toString(is);
		return new JSONObject(new JSONTokener(content));
	}

	public HttpResponse getResponse(final String path,
			final String mediaType) throws IOException, ClientProtocolException {
		final HttpGet get = new HttpGet(getBaseUrl() + path);
		get.addHeader("Accept", mediaType);
		return getClient().execute(get);
	}
}