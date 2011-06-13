package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RestResourceTestHelper {

	public HttpResponse doDelete(final String path)
			throws ClientProtocolException, IOException {
		final HttpDelete delete = new HttpDelete(getBaseUrl() + path);
		return getClient().execute(delete);
	}

	public HttpResponse doGet(final String path, final String mediaType)
			throws IOException, ClientProtocolException {
		final HttpGet get = new HttpGet(getBaseUrl() + path);
		get.addHeader("Accept", mediaType);
		return getClient().execute(get);
	}

	public HttpResponse doPost(final String path, final String payload,
			String mediaType) throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		final HttpPost post = new HttpPost(getBaseUrl() + path);
		post.addHeader("Accept", mediaType);
		post.setEntity(new StringEntity(payload, mediaType, "UTF-8"));
		return getClient().execute(post);
	}

	public HttpResponse doPut(final String path, final String mediaType)
			throws ClientProtocolException, IOException {
		final HttpPut put = new HttpPut(getBaseUrl() + path);
		put.addHeader("Accept", mediaType);
		return getClient().execute(put);
	}

	public String getBaseUrl() {
		return "http://localhost:8080/resources";
	}

	public HttpClient getClient() {
		return new DefaultHttpClient(new ThreadSafeClientConnManager());
	}

	public InputStream getContentStream(final HttpResponse response)
			throws IllegalStateException, IOException {
		return response.getEntity().getContent();
	}

	public JSONArray getJsonArrayResponse(final String path)
			throws ClientProtocolException, IOException, JSONException {
		final HttpResponse rsp = doGet(path, APPLICATION_JSON);
		final InputStream content = getContentStream(rsp);
		return new JSONArray(new JSONTokener(new InputStreamReader(content)));
	}

	public JSONObject getJsonResponse(final String path)
			throws ClientProtocolException, IOException, JSONException {
		final HttpResponse rsp = doGet(path, APPLICATION_JSON);
		final InputStream content = getContentStream(rsp);
		return new JSONObject(new JSONTokener(new InputStreamReader(content)));
	}

	public <T> T getXmlResponse(final String path,
			final Class<T> expectedClass) throws JAXBException, IOException,
			ClientProtocolException, FactoryConfigurationError,
			XMLStreamException {
		final HttpResponse rsp = doGet(path, APPLICATION_XML);
		final InputStream content = getContentStream(rsp);
		return unMarshall(content, expectedClass);
	}

	public JSONObject postJsonRequestAndGetJsonResponse(final String path,
			final String payload) throws ClientProtocolException, IOException,
			JSONException {
		final HttpResponse rsp = doPost(path, payload, APPLICATION_JSON);
		final InputStream content = getContentStream(rsp);
		return new JSONObject(new JSONTokener(new InputStreamReader(content)));
	}

	public <T> T postXmlRequestAndGetXmlResponse(final String path,
			final String payload, final Class<T> expectedClass)
			throws ClientProtocolException, IOException, XMLStreamException,
			JAXBException {
		final HttpResponse rsp = doPost(path, payload, APPLICATION_XML);
		final InputStream content = getContentStream(rsp);
		return unMarshall(content, expectedClass);
	}

	public JSONObject putAndGetJsonResponse(final String path)
			throws ClientProtocolException, IOException, JSONException {
		final HttpResponse rsp = doPut(path, APPLICATION_JSON);
		final InputStream content = getContentStream(rsp);
		return new JSONObject(new JSONTokener(new InputStreamReader(content)));
	}

	public <T> T putAndGetXmlResponse(final String path,
			final Class<T> expectedClass) throws ClientProtocolException,
			IOException, FactoryConfigurationError, XMLStreamException,
			JAXBException {
		final HttpResponse rsp = doPut(path, APPLICATION_XML);
		final InputStream content = getContentStream(rsp);
		return unMarshall(content, expectedClass);
	}

	public <T> T unMarshall(final InputStream contentStream,
			final Class<T> expectedClass) throws FactoryConfigurationError,
			XMLStreamException, JAXBException {
		final XMLInputFactory f = XMLInputFactory.newFactory();
		final XMLStreamReader reader = f.createXMLStreamReader(contentStream);
		final JAXBContext ctx = JAXBContext.newInstance(expectedClass);
		final Unmarshaller um = ctx.createUnmarshaller();
		final JAXBElement<T> el = um.unmarshal(reader, expectedClass);
		return el.getValue();
	}
}