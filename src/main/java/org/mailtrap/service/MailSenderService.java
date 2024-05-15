package org.mailtrap.service;

import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface MailSenderService {

    ResponseDto sendEmail(MailSenderDto mailSenderDto, List<MultipartFile> attachments);
}
