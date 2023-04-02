package com.example.demo.exception;

import com.example.demo.controller.dto.MessageResponseDto;
import com.example.demo.util.MessageSourceUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler {

    private static final Logger log = LogManager.getLogger(ResponseExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final MessageResponseDto handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("handleConstraintViolationException", exception.getCause());

        String detailMessage = exception.getMessage();
        String[] messages = detailMessage.contains(",") ? detailMessage.split(",") : new String[]{detailMessage};

        List<String> errors = new ArrayList<>();
        Arrays.stream(messages).forEach(msg ->{
            if (msg.contains(":")) {
                String[] detailMessageArr = msg.split(":");
                if (detailMessageArr.length == 2) {
                    String key = detailMessageArr[1];
                    String message = MessageSourceUtilities.getValue(key.trim()).getMessage();
                    if (MessageSourceUtilities.DESCRIPTION_NOT_FOUND.equals(message)) {
                        message = detailMessageArr[0].split("\\.")[detailMessageArr[0].split("\\.").length - 1] + ":" + key;
                    }
                    errors.add(message);
                }
            }
        });

        Collections.sort(errors);
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request.url"), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final MessageResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request"), getErrors(exception.getBindingResult()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProjectNotFoundException.class)
    public final MessageResponseDto handleProjectNotFoundException(ProjectNotFoundException exception) {
        log.error("handleProjectNotFoundException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.project.not.found"));
    }

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ExceptionHandler(ExternalServiceException.class)
    public final MessageResponseDto handleExternalServiceException(ExternalServiceException exception) {
        log.error("handleExternalServiceException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.external.service"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public final MessageResponseDto handleBindException(BindException exception) {
        log.error("handleBindException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request"), getErrors(exception.getBindingResult()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProjectDeleteException.class)
    public final MessageResponseDto handleProjectDeleteException(ProjectDeleteException exception) {
        log.error("handleProjectDeleteException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.project.delete"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final MessageResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("handleHttpMessageNotReadableException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.invalid.request.body"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ContactNotFoundException.class)
    public final MessageResponseDto handleContactNotFoundException(ContactNotFoundException exception) {
        log.error("handleContactNotFoundException", exception.getCause());
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.contact.not.found"));
    }

    private List<String> getErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            String msg = error.getDefaultMessage();
            if (msg != null && msg.contains(".")) {
                msg = MessageSourceUtilities.getValue(error.getDefaultMessage()).getMessage();
            }
            errors.add(error.getField() + ": " + msg);
        });

        bindingResult.getGlobalErrors().forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
        Collections.sort(errors);
        return errors;
    }
}
