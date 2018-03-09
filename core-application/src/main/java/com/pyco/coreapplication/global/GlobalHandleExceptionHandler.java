package com.pyco.coreapplication.global;

import com.pyco.coreapplication.util.ErrorUtil;
import com.pyco.coreapplication.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class GlobalHandleExceptionHandler {

    public static final String ERROR_MORE_INFO_GLOBAL = "Please contact admin with errorId to know more information.";
    public static final String ERROR_MESSAGE_GLOBAL = "There is an error during handling this request";

    private static Logger logger = LoggerFactory.getLogger(GlobalHandleExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected static ResponseEntity<ErrorDto> handleGlobalException(final Exception e) {
        UUID errorId = ErrorUtil.logError(logger, e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(errorId.toString(), ERROR_MESSAGE_GLOBAL, ERROR_MORE_INFO_GLOBAL));
    }

}



