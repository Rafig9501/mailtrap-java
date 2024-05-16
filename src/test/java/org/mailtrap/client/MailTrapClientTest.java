package org.mailtrap.client;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailtrap.exception.ClientException;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {MailTrapClient.class})
@ExtendWith(SpringExtension.class)
class MailTrapClientTest {

    @Autowired
    private MailTrapClient mailTrapClient;

    @MockBean
    private OkHttpClient okHttpClient;

    @MockBean
    private Call mockedCall;

    @Test
    void testSendEmail_Success() throws IOException {
        String jsonResponse = "{\"status\": \"success\"}";
        ResponseBody mockedBody = ResponseBody.create(jsonResponse, okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE));
        Response mockedResponse = new Response.Builder()
                .request(new Request.Builder().url("http://example.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(mockedBody)
                .build();
        when(okHttpClient.newCall(any())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(mockedResponse);
        ReflectionTestUtils.setField(mailTrapClient, "MAILTRAP_API", "http://example.com");
        ReflectionTestUtils.setField(mailTrapClient, "MAILTRAP_TOKEN", "token");
        CompletableFuture<String> result = mailTrapClient.sendEmail("testRequestJson");
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(okHttpClient).newCall(requestCaptor.capture());
        Request sentRequest = requestCaptor.getValue();
        assertEquals("http://example.com/", sentRequest.url().toString());
        assertEquals(HttpMethod.POST.name(), sentRequest.method());
        assertEquals("Bearer token", sentRequest.header(HttpHeaders.AUTHORIZATION));
        assertEquals(MediaType.APPLICATION_JSON_VALUE, sentRequest.header(HttpHeaders.CONTENT_TYPE));
        assertEquals(jsonResponse, result.join());
    }

    @Test
    void testSendEmail_Exception() throws IOException {
        when(okHttpClient.newCall(any())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(new IOException("Connection error"));
        ReflectionTestUtils.setField(mailTrapClient, "MAILTRAP_API", "http://example.com");
        ReflectionTestUtils.setField(mailTrapClient, "MAILTRAP_TOKEN", "token");
        try {
            CompletableFuture<String> result = mailTrapClient.sendEmail("testRequestJson");
            result.join();
        } catch (Exception e) {
            assertEquals(ClientException.class, e.getClass());
            assertEquals("Connection error", e.getMessage());
        }
    }
}
