package com.example.backend.config.exceptionHandle;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandle {

    @ApiResponse(
            responseCode = "404",
            description = "Currículo não encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = NotFoundException.class)
            ))
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ApiResponse(
            responseCode = "500",
            description = "Ocorreu um erro interno no servidor.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Exception.class)
            ))
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
