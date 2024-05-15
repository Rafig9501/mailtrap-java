package org.mailtrap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.client.ResponseDto;
import org.mailtrap.service.MailSenderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody MailSenderDto dto) {
        return ResponseEntity.ok(mailSenderService.sendEmail(dto));
    }
}