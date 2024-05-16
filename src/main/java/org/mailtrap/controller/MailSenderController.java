package org.mailtrap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.service.MailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/mail-sender")
@RequiredArgsConstructor
@Validated
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @Operation(description = "Send Email",
            responses = @ApiResponse(responseCode = "200"))
    @PostMapping(path = "/send",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<ResponseDto>> sendEmail(@RequestPart MailSenderDto dto,
                                                                    @RequestPart(required = false) List<MultipartFile> attachments) {
        return mailSenderService.sendEmail(dto, attachments)
                .thenApplyAsync(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}