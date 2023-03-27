package com.example.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseDto {

    private String message;
    private String code;

    private List<String> errors;

    public MessageResponseDto(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public MessageResponseDto(MessageResponseDto messageResponseDto) {
        this.message = messageResponseDto.getMessage();
        this.code = messageResponseDto.getCode();
    }


    public MessageResponseDto(MessageResponseDto messageResponseDto, List<String> errors) {
        this.message = messageResponseDto.getMessage();
        this.code = messageResponseDto.getCode();
        this.errors = errors;
    }
}
