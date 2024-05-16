package org.mailtrap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentRequestDto {

    private String fileName;
    private String fileType;
    private String fileDisposition;
    private String fileContentId;
}
