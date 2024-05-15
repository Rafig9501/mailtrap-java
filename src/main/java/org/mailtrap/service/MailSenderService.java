package org.mailtrap.service;

import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.springframework.stereotype.Service;


@Service
public interface MailSenderService {

    ResponseDto sendEmail(MailSenderDto mailSenderDto);
}
