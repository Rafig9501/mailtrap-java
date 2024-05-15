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
public class RecipientInfoDto {

    private String emailAddress;
    private String recipientType;
}