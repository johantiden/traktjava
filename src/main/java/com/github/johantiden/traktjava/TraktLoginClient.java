package com.github.johantiden.traktjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johantiden.traktjava.dto.TokenDto;
import com.github.johantiden.traktjava.internal.TraktHttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import java.util.Objects;

public class TraktLoginClient {

    public static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private final TraktHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String clientId;
    private final String clientSecret;

    public TraktLoginClient(TraktHttpClient httpClient, ObjectMapper objectMapper, String clientId, String clientSecret) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.clientId = Objects.requireNonNull(clientId);
        this.clientSecret = Objects.requireNonNull(clientSecret);
    }

    public TraktToken getAccessToken(String oauthTempCode) {


        String uri = "https://api.trakt.tv/oauth/token";
//                +
//                "?" +
//                "code="+oauthTempCode +
//                "client_id="+TraktTvClient.CLIENT_ID +
//                "client_secret="+TraktTvClient.CLIENT_SECRET;

        LoginRequestDto requestDto = new LoginRequestDto(
                oauthTempCode,
                clientId,
                clientSecret,
                REDIRECT_URI,
                "authorization_code"
        );
        String body = null;
        try {
            body = objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpUriRequest request = RequestBuilder.post(uri)
                .addHeader("Content-Type", "application/json")
                .setEntity(new StringEntity(body, "UTF-8"))
                .build();


        TokenDto dto = httpClient.executeTo(request, new TypeReference<TokenDto>() {});
        return fromDto(dto);
    }

    public String getAuthorizationUrl() {
        return "https://api.trakt.tv/oauth/authorize?" +
                "response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI;
    }

    private static TraktToken fromDto(TokenDto dto) {
        return new TraktToken(dto.token);
    }

}
