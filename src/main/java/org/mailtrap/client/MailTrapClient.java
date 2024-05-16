package org.mailtrap.client;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.mailtrap.exception.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@PropertySource("classpath:mailtrap.properties")
@RequiredArgsConstructor
public class MailTrapClient {

    @Value("${mailtrap.api}")
    private String MAILTRAP_API;

    @Value("${mailtrap.token}")
    private String MAILTRAP_TOKEN;

    private final OkHttpClient client;

    @Async
    public CompletableFuture<String> sendEmail(String requestJson) {
        RequestBody requestBody = RequestBody.create(requestJson, MediaType.parse(APPLICATION_JSON_VALUE));
        Request httpRequest = new Request.Builder()
                .url(MAILTRAP_API)
                .method(POST.name(), requestBody)
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .addHeader(AUTHORIZATION, "Bearer " + MAILTRAP_TOKEN)
                .build();
        try (Response response = client.newCall(httpRequest).execute()){
            if (response.body() != null) {
                return CompletableFuture.completedFuture(response.body().string());
            }
            return CompletableFuture.completedFuture(null);
        } catch (IOException e) {
            throw new ClientException(e.getMessage());
        }
    }
}