package org.mailtrap.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDto {

    private String email;
    private String name;
}