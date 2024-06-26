package org.mailtrap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.mailtrap.dto.client.EmailDto;


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
}
