package com.employee.EMPLOYEE.config;

import com.employee.EMPLOYEE.exception.BadRequestException;
import com.employee.EMPLOYEE.exception.CustomException;
import com.employee.EMPLOYEE.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        if (response.status() == 404 || response.status() == 503) {
            return new BadRequestException("Employee server is down try later");
        }

        try (InputStream is = response.body().asInputStream()) {
            ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);

            return new CustomException(errorResponse.getMessage(), errorResponse.getStatus());
        } catch (IOException e) {

            return new CustomException("INTERNAL_ server error");
        }
    }
}
