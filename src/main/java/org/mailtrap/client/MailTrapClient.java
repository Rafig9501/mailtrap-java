package org.mailtrap.client;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    private final Gson gson;
    private final MailMapper mailMapper;

    public ResponseDto sendEmail(MailSenderDto dto) {
        MailTrapRequestDto request = mailMapper.mailSenderDtoToMailTrapRequestDto(dto);
        String json = gson.toJson(request);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse(APPLICATION_JSON_VALUE));

        Request httpRequest = new Request.Builder()
                .url(MAILTRAP_API)
                .method(POST.name(), requestBody)
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .addHeader(AUTHORIZATION, "Bearer " + MAILTRAP_TOKEN)
                .build();
        try (Response response = client.newCall(httpRequest).execute()){
            if (response.body() != null) {
                return gson.fromJson(response.body().string(), ResponseDto.class);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
