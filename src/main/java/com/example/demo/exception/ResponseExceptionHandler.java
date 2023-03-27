package com.example.demo.exception;

import com.example.demo.controller.dto.MessageResponseDto;
import com.example.demo.util.MessageSourceUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(ResponseExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final MessageResponseDto handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("handleConstraintViolationException", exception.getCause());

        String detailMessage = exception.getMessage();
        String[] messages = null;
        if (detailMessage.contains(",")) {
            messages = detailMessage.split(",");
        } else {
            messages = new String[]{detailMessage};

        }
        List<String> errors = new ArrayList<>();
        for (String msg : messages) {
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
        }

        Collections.sort(errors);
        return new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request.url"), errors);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request"), getErrors(ex.getBindingResult())), HttpStatus.BAD_REQUEST);
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

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new MessageResponseDto(MessageSourceUtilities.getValue("msg.error.bad.request"), getErrors(ex.getBindingResult())), HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            String msg = error.getDefaultMessage();
            if (msg != null && msg.contains(".")) {
                msg = MessageSourceUtilities.getValue(error.getDefaultMessage()).getMessage();
            }
            errors.add(error.getField() + ": " + msg);
        }
        for (ObjectError error : bindingResult.getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        Collections.sort(errors);
        return errors;
    }
}
