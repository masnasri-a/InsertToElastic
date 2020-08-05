package org.example;

import jdk.nashorn.api.scripting.JSObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClientTest67 {

    private static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        // The default cache size is 100 MiB. Change it to 30 MiB.
        builder.setHttpAsyncResponseConsumerFactory( new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    public static void main(String[] args) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("{username}","{password}"));
        HttpHost[] hosts;
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost",9200)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        RestHighLevelClient highClient = new RestHighLevelClient(builder);

        try {
            Map<String, Object> json = new HashMap<>();
            json.put("Name","Heheheh");
            json.put("Email","Nasriariaijr@gmail.com");
            List<String> Detail =  new ArrayList<>();
            Detail.add("Dota 2");
            Detail.add("Valorant");
            json.put("Game",Detail);

            IndexRequest indexRequest = new IndexRequest("whuts","_doc","2").source(json);
            IndexResponse indexResponse = highClient.index(indexRequest,COMMON_OPTIONS);

            long version = indexResponse.getVersion();

            System.out.println("Index Document Successfully "+version);

//            DeleteRequest request = new DeleteRequest("whuts","_doc","1");
//            DeleteResponse deleteResponse = highClient.delete(request, COMMON_OPTIONS);
//            System.out.println("Delete document successfully! \n"+deleteResponse.toString()+"\n"+deleteResponse.status());
            highClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
