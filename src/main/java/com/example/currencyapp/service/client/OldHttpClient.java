package com.example.currencyapp.service.client;

import com.example.currencyapp.exception.DataProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OldHttpClient {
    private final ObjectMapper objectMapper;

    public <T> T getData(Class<? extends T> clazz, String url) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return objectMapper.readValue(result, clazz);
            }
        } catch (IOException e) {
            throw new DataProcessingException("Http request failed", e);
        }
        throw new DataProcessingException("Http request failed");
    }
}
