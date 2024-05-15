package org.mailtrap.mapper;

import org.mailtrap.dto.AttachmentRequestDto;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.RecipientDto;
import org.mailtrap.dto.client.AttachmentDto;
import org.mailtrap.dto.client.EmailDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.mapstruct.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MailMapper {

    @Mapping(source = "to", target = "to", qualifiedByName = "mapRecipients")
    @Mapping(source = "cc", target = "cc", qualifiedByName = "mapRecipients")
    @Mapping(source = "bcc", target = "bcc", qualifiedByName = "mapRecipients")
    @Mapping(source = "emailFrom", target = "from")
    @Mapping(source = "attachment", target = "attachments", qualifiedByName = "mapAttachments")
    MailTrapRequestDto mailSenderDtoToMailTrapRequestDto(MailSenderDto dto);

    @Named("mapRecipients")
    default List<EmailDto> mapRecipients(RecipientDto dto) {
        return dto != null && dto.getRecipients() != null && !dto.getRecipients().isEmpty() ? dto.getRecipients()
                : List.of();
    }

    @Named("mapAttachments")
    default List<AttachmentDto> mapAttachments(List<AttachmentRequestDto> attachments) {
        return attachments
                .stream()
                .map(a -> {
                            try {
                                return AttachmentDto.builder()
                                        .content(Arrays.toString(a.getAttachment().getBytes()))
                                        .type(a.getFileType())
                                        .disposition(a.getFileDisposition())
                                        .filename(a.getFileName())
                                        .build();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .collect(Collectors.toList());
    }
}
