package com.example.currencyapp.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper;

    public <T> T get(Class<? extends T> clazz, String url) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't fetch data from url " + url, e);
        }
    }
}
