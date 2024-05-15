package org.mailtrap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
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
    private String emailFrom;
    private String recipientName;
    private String subject;
    private String text;
    private String html;
    private MultipartFile attachment;
    private String category;
    private String attachmentName;
    private String attachmentType;
    private String attachmentDisposition;
}
