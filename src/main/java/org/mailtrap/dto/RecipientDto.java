package org.mailtrap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.mailtrap.dto.client.EmailDto;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipientDto {

    List<EmailDto> recipients;
}
