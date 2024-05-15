package org.mailtrap.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody MailSenderDto dto) {
        return ResponseEntity.ok(mailSenderService.sendEmail(dto));
    }
}