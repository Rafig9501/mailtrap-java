package org.mailtrap.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailtrap.dto.MailSenderDto;
import org.mailtrap.dto.RecipientDto;
import org.mailtrap.dto.client.EmailDto;
import org.mailtrap.dto.client.MailTrapRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ContextConfiguration(classes = {MailMapperImpl.class})
@ExtendWith(SpringExtension.class)
class MailMapperTest {

    @Autowired
    private MailMapper mailMapper;

    @Test
    void testNullDto(){
        assertNull(mailMapper.mailSenderDtoToMailTrapRequestDto(null));
    }

    @Test
    void testMailSenderDtoToMailTrapRequestDto() {
        MailSenderDto mailSenderDto = createMailSenderDto();
        MailTrapRequestDto result = mailMapper.mailSenderDtoToMailTrapRequestDto(mailSenderDto);
        assertEquals(1, result.getTo().size());
        assertEquals(1, result.getCc().size());
        assertEquals(0, result.getBcc().size());
        assertEquals("from@example.com", result.getFrom().getEmail());
        assertEquals("Test Subject", result.getSubject());
        assertEquals("Test Text", result.getText());
        assertEquals("Test HTML", result.getHtml());
    }

    @Test
    void testMapRecipients() {
        RecipientDto recipientDto = new RecipientDto(Collections.singletonList(new EmailDto("to@example.com", "To")));
        List<EmailDto> result = mailMapper.mapRecipients(recipientDto);
        assertEquals(1, result.size());
        assertEquals("to@example.com", result.get(0).getEmail());
        assertEquals("To", result.get(0).getName());
    }

    private MailSenderDto createMailSenderDto() {
        RecipientDto to = new RecipientDto(Collections.singletonList(new EmailDto("to@example.com", "To")));
        RecipientDto cc = new RecipientDto(Collections.singletonList(new EmailDto("cc@example.com", "CC")));
        EmailDto emailFrom = new EmailDto("from@example.com", "From");
        return MailSenderDto.builder()
                .to(to)
                .cc(cc)
                .emailFrom(emailFrom)
                .subject("Test Subject")
                .text("Test Text")
                .html("Test HTML")
                .build();
    }
}

