package org.example;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, RuntimeException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
//        IndexRequest request = new IndexRequest("testing12");
//        request.id("1");
//        String jsonString = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
//        request.source(jsonString, XContentType.JSON);


        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("post_date", new Date());
        jsonMap.put("Message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("masnasri12")
                .id("5").source(jsonMap);
        client.close();

        GetRequest gr = new GetRequest("masnasri12", "5");
        final GetResponse gres = client.get(gr, RequestOptions.DEFAULT);

        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse documentFields) {
                String index = gres.getIndex();
                String id = gres.getId();
                if (gres.isExists()) {
                    long version = gres.getVersion();
                    String sourceAsString = gres.getSourceAsString();
                    Map<String, Object> sourceAsMap = gres.getSourceAsMap();
                    byte[] sourceAsByte = gres.getSourceAsBytes();
                }
                System.out.println(index);
                System.out.println(id);
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

    }


}
