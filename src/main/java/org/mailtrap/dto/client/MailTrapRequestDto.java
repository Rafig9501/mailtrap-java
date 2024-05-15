package org.mailtrap.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;


@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailTrapRequestDto {

    private List<EmailDto> to;
    private List<EmailDto> cc;
    private List<EmailDto> bcc;
    private EmailDto from;
    private List<AttachmentDto> attachments;
    private Map<String, String> headers;
    private String subject;
    private String text;
    private String html;
    private String category;

    @JsonProperty("custom_variables")
    private Map<String, String> customVariables;
}
