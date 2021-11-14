/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.google.gson.Gson;
import cst8218.nguy0770.entity.Sprite;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.XML;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import static org.hamcrest.CoreMatchers.equalTo;
import org.json.JSONArray;
import static org.junit.Assert.assertThat;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: TestSpriteREST
 * Description: Test Sprite's RESTful methods of GET, POST, PUT, and DELETE
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSpriteREST {
    
    /**
     * 
     * Tests POST method for creating new Sprite
     * @throws IOException - if basic authentication fails
     */
    @Test
    public void testCase1() throws IOException {
        String newSpriteBody = "{ \"dx\": 8,"
                + " \"dy\": 5,"
                + "\"panelHeight\": 500,"
                + "\"panelWidth\": 500,"
                + "\"x\": 100,"
                + "\"y\": 25}";

        StringEntity requestEntity = new StringEntity(newSpriteBody, ContentType.APPLICATION_JSON);

        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("notsam", "123123123");
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost postMethod = new HttpPost("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite");
        postMethod.setEntity(requestEntity);

        HttpResponse httpResponse = client.execute(postMethod, context);

        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_CREATED));
    }
    
    /**
     * 
     * Test GET by ID method by getting non-exist Sprite at id = -1
     * @throws IOException - if basic authentication fails
     */
    @Test
    public void testCase2() throws IOException {
        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("notsam", "123123123");
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        HttpClient client = HttpClientBuilder.create().build();

        String id = "-1";
        HttpUriRequest request = new HttpGet("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite/" + id);

        HttpResponse httpResponse = client.execute(request, context);

        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }
    
    /**
     * 
     * Test PUT by ID method by using the ID of the Sprite created at testCase1
     * @throws IOException - if basic authentication fails
     */
    @Test
    public void testCase3() throws IOException {
        String newSpriteBody = "{ \"dx\": 8,"
                + " \"dy\": 5,"
                + "\"panelHeight\": 500,"
                + "\"panelWidth\": 500,"
                + "\"id\": -1,"
                + "\"x\": 100,"
                + "\"y\": 25}";

        StringEntity requestEntity = new StringEntity(newSpriteBody, ContentType.APPLICATION_JSON);

        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("notsam", "123123123");
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet getMethod = new HttpGet("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite");

        HttpResponse getResponse = client.execute(getMethod, context);
        
        long id = returnID(getResponse);

        HttpPut putMethod = new HttpPut("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite/" + id);
        putMethod.setEntity(requestEntity);

        HttpResponse httpResponse = client.execute(putMethod, context);

        StringBuilder message = new StringBuilder();
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()), 65728);
        String line = null;

        while ((line = reader.readLine()) != null) {
            message.append(line);
        }

        assertThat(message.toString(), equalTo("Sprite's body has non-matching id"));
    }

    /**
     * 
     * Test DELETE by ID method by deleting the Sprite created at testCase1
     * @throws IOException - if basic authentication fails
     */
    @Test
    public void testCase4() throws IOException {
        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("notsam", "123123123");
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet getMethod = new HttpGet("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite");

        HttpResponse httpResponse = client.execute(getMethod, context);

        long id = returnID(httpResponse);

        HttpDelete deleteMethod = new HttpDelete("http://localhost:8080/SpriteSong-war/webresources/cst8218.nguy0770.entity.sprite/" + id);

        HttpResponse deletionResponse = client.execute(deleteMethod, context);

        assertThat(deletionResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_ACCEPTED));
    }
    
    /**
     * 
     * @param httpResponse - response from GET method
     * @return - the ID of the last Sprite in the list
     * @throws IOException - if Sprite list is empty
     */
    private long returnID(HttpResponse httpResponse) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        String json = reader.readLine();
        
        Object xmlJSONObj = XML.toJSONObject(json).getJSONObject("sprites").get("sprite");

        long id = 0;
        
        if (xmlJSONObj instanceof JSONArray) {
            List<Sprite> spriteList = new ArrayList<Sprite>();
            JSONArray sprites = (JSONArray) xmlJSONObj;
            for (int i = 0; i < sprites.length(); i++) {
                JSONObject a = sprites.getJSONObject(i);
                a.remove("color");
                spriteList.add(new Gson().fromJson(a.toString(), Sprite.class));
            }
            
            id = spriteList.get(spriteList.size() - 1).getId();
        } else {
            JSONObject sprite = (JSONObject) xmlJSONObj;
            sprite.remove("color");
            Sprite spriteToDelete = new Gson().fromJson(sprite.toString(), Sprite.class);
            id = spriteToDelete.getId();
        }
        
        return id;
    }
}
