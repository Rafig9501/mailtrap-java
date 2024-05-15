package org.mailtrap.service.impl;

import lombok.RequiredArgsConstructor;
import org.mailtrap.client.MailTrapClient;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.service.MailSenderService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final MailTrapClient mailTrapClient;

    @Override
    public ResponseDto sendEmail(MailSenderDto dto) {
        return mailTrapClient.sendEmail(dto);
    }
}
