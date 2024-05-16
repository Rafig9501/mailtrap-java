package org.mailtrap.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.mailtrap.client.MailTrapClient;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.AttachmentDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.mapper.MailMapper;
import org.mailtrap.service.MailSenderService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final MailTrapClient mailTrapClient;
    private final MailMapper mailMapper;
    private final Gson gson;

    @Override
    public CompletableFuture<ResponseDto> sendEmail(MailSenderDto dto, List<MultipartFile> attachments) {
        return CompletableFuture.supplyAsync(() -> {
            MailTrapRequestDto mailTrapRequest = mailMapper.mailSenderDtoToMailTrapRequestDto(dto);
            List<AttachmentDto> attachmentDtoList = attachments.stream()
                    .map(attachment -> {
                        try {
                            AttachmentDto attachmentDto = new AttachmentDto();
                            attachmentDto.setContent(Base64.getEncoder().encodeToString(attachment.getBytes()));
                            attachmentDto.setFilename(attachment.getOriginalFilename());
                            attachmentDto.setType(attachment.getContentType());
                            attachmentDto.setDisposition("attachment");
                            return attachmentDto;
                        } catch (IOException e) {
                            throw new RuntimeException("Error processing attachment: " + attachment.getOriginalFilename(), e);
                        }
                    })
                    .toList();
            mailTrapRequest.setAttachments(attachmentDtoList);
            String jsonRequest = gson.toJson(mailTrapRequest);
            String jsonResponse = mailTrapClient.sendEmail(jsonRequest).join();
            return gson.fromJson(jsonResponse, ResponseDto.class);
        });
    }
}
