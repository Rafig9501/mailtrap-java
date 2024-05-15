package org.mailtrap.mapper;

import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.RecipientDto;
import org.mailtrap.dto.client.EmailDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MailMapper {

    @Mapping(source = "to", target = "to", qualifiedByName = "mapRecipients")
    @Mapping(source = "cc", target = "cc", qualifiedByName = "mapRecipients")
    @Mapping(source = "bcc", target = "bcc", qualifiedByName = "mapRecipients")
    MailTrapRequestDto mailSenderDtoToMailTrapRequestDto(MailSenderDto dto);

    @Named("mapRecipients")
    default List<EmailDto> mapRecipients(RecipientDto dto) {
        return dto.getRecipients();
    }
}
