package org.mailtrap.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.mailtrap.dto.client.AttachmentDto;
import org.mailtrap.dto.client.EmailDto;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailSenderDto {

    private RecipientDto to;
    private RecipientDto cc;
    private RecipientDto bcc;
    private EmailDto emailFrom;
    private String subject;
    private String text;
    private String html;
    private List<AttachmentRequestDto> attachment;
}
