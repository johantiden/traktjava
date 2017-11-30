package com.github.johantiden.traktjava.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johantiden.traktjava.TraktToken;
import com.github.johantiden.traktjava.TraktTvClient;
import com.github.johantiden.traktjava.dto.ErrorDto;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class TraktHttpClient {


    private static final Logger log = LoggerFactory.getLogger(TraktHttpClient.class);
    private final String clientId;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TraktHttpClient(CloseableHttpClient httpClient, ObjectMapper objectMapper, String clientId) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.clientId = Objects.requireNonNull(clientId);
    }

    public <T> T get(String url, TraktToken token, TypeReference<T> typeLiteral) {
        return executeTo(buildGet(url, token), typeLiteral);
    }

    private HttpUriRequest buildGet(String url, TraktToken token) {

        return RequestBuilder.get(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token.getToken())
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", clientId)
                .build();
    }

    private HttpUriRequest buildPost(String url, TraktToken token, String body) {
        return RequestBuilder.post(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token.getToken())
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", clientId)
                .setEntity(new StringEntity(body, "UTF-8"))
                .build();
    }

    private HttpUriRequest buildDelete(String url, TraktToken token) {
        return RequestBuilder.delete(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token.getToken())
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", clientId)
                .build();
    }

    public <T> T post(String url, TraktToken traktToken, Object body, TypeReference<T> typeLiteral) {
        String bodyString;
        try {
            bodyString = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException ignored) {
            throw new RuntimeException("Failed to write object to JSON");
        }
        HttpUriRequest httpUriRequest = buildPost(url, traktToken, bodyString);
        return executeTo(httpUriRequest, typeLiteral);
    }

    public void post(String url, TraktToken traktToken, Object body) {
        post(url, traktToken, body, new TypeReference<Object>() {});
    }

    public String executeToString(HttpUriRequest request) {
        try (CloseableHttpResponse execute = httpClient.execute(request)) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            execute.getEntity().writeTo(baos);
            String body = baos.toString();
//            log.debug("body: {}", body);
            if (execute.getStatusLine().getStatusCode() <= 299) {
                return body;
            } else {
                log.error("{}  --  {}:{}", request.getURI(), execute.getStatusLine().getStatusCode(), execute.getStatusLine().getReasonPhrase());

                String headers = Arrays.stream(execute.getAllHeaders())
                        .map(h -> h.getName() + ": " + h.getValue())
                        .reduce((s1, s2) -> s1 + "\n" + s2)
                        .orElse("");
                log.error(headers);

                if (!body.isEmpty()) {
                    try {
                        ErrorDto dto = objectMapper.readValue(body, new TypeReference<ErrorDto>() {
                        });
                        throw new RuntimeException("Failed to execute request\n" + dto.error + ":" + dto.errorDescription);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to parse errorDto.", e);
                    }
                } else {
                    throw new RuntimeException("Failed to execute request. Body was empty");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed get request: " +request.getURI(), e);
        }

    }

    public <T> T executeTo(HttpUriRequest request, TypeReference<T> typeReference) {
        String body = executeToString(request);
        try {
            return objectMapper.readValue(body, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Could not parse dto", e);
        }
    }

    public void delete(String url, TraktToken traktToken) {
        HttpUriRequest httpUriRequest = buildDelete(url, traktToken);
        executeToString(httpUriRequest);
    }
}
