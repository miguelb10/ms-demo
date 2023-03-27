package com.example.demo.util;

import com.example.demo.controller.dto.MessageResponseDto;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageSourceUtilities {

    public static final String DESCRIPTION_NOT_FOUND = "description not found";
    public static final String CODE_NOT_FOUND = "code not found";
    private static final ResourceBundle messagesResourceBundle = ResourceBundle.getBundle("message");
    private static final ResourceBundle codesResourceBundle = ResourceBundle.getBundle("code");

    private MessageSourceUtilities() {
    }

    public static MessageResponseDto getValue(String key) {
        String message = getMessage(key);
        String code = getCode(key);
        return new MessageResponseDto(message, code);
    }

    private static String getMessage(String key) {
        try {
            return messagesResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return DESCRIPTION_NOT_FOUND;
        }
    }

    private static String getCode(String key) {
        try {
            return codesResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return CODE_NOT_FOUND;
        }
    }
}
