package org.mailtrap.service.impl;

import java.util.List;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.mailtrap.client.MailTrapClient;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.mapper.MailMapper;
import org.mailtrap.service.MailSenderService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final MailTrapClient mailTrapClient;
    private final MailMapper mailMapper;
    private final Gson gson;

    @Override
    public ResponseDto sendEmail(MailSenderDto dto) {
        MailTrapRequestDto mailTrapRequest = mailMapper.mailSenderDtoToMailTrapRequestDto(dto);
        String jsonRequest = gson.toJson(mailTrapRequest);
        String jsonResponse = mailTrapClient.sendEmail(jsonRequest);
        return gson.fromJson(jsonResponse, ResponseDto.class);
    }
}
