package org.mailtrap.service;

import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public interface MailSenderService {

    CompletableFuture<ResponseDto> sendEmail(MailSenderDto mailSenderDto, List<MultipartFile> attachments);
}
