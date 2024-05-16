package org.mailtrap.service.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailtrap.client.MailTrapClient;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {MailSenderServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MailSenderServiceImplTest {

    @Autowired
    private MailSenderServiceImpl mailSenderService;

    @MockBean
    private MailTrapClient mailTrapClient;

    @MockBean
    private MailMapper mailMapper;

    @MockBean
    private Gson gson;

    @Test
    void testSendEmail() throws IOException {
        MailSenderDto mailSenderDto = new MailSenderDto();
        MailTrapRequestDto mailTrapRequestDto = new MailTrapRequestDto();
        ResponseDto responseDto = new ResponseDto();
        String jsonRequest = "{\"key\": \"value\"}";
        String jsonResponse = "{\"key\": \"response\"}";
        when(mailMapper.mailSenderDtoToMailTrapRequestDto(any())).thenReturn(mailTrapRequestDto);
        when(gson.toJson(any())).thenReturn(jsonRequest);
        when(mailTrapClient.sendEmail(any())).thenReturn(CompletableFuture.completedFuture(jsonResponse));
        when(gson.fromJson(jsonResponse, ResponseDto.class)).thenReturn(responseDto);
        CompletableFuture<ResponseDto> result = mailSenderService.sendEmail(mailSenderDto,
                List.of(new MockMultipartFile("Name", new ByteArrayInputStream("foo".getBytes(StandardCharsets.UTF_8)))));
        assertEquals(responseDto, result.join());
    }
}