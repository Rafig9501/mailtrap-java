package org.mailtrap.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {

    public static final String INPUT_VALIDATION_EXCEPTION = "Input validation has been failed";
    public static final String INTERNAL_ERROR = "Internal error has been occurred";
    public static final String METHOD_NOT_SUPPORTED_EXCEPTION = "Method is not supported";
    public static final String BAD_REQUEST_EXCEPTION = "Request body validation has failed due to some reasons";
    public static final String SERVICE_UNAVAILABLE_EXCEPTION = "Mailtrap service unavailable";
    public static final String ATTACHMENT_EXCEPTION = "Cannot map attachment";
}