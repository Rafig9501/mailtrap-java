package org.mailtrap.service;

import java.util.List;

import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface MailSenderService {

    ResponseDto sendEmail(MailSenderDto mailSenderDto);
}
