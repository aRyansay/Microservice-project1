package com.address.Address.config;

import com.address.Address.exception.BadRequestException;
import com.address.Address.exception.CustomException;
import com.address.Address.exception.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 503) {
            return new BadRequestException("Employee server is down try  2 later" ,HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.status() == 404) {
            return new BadRequestException("Employee not found", HttpStatus.NOT_FOUND);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();


//        if (response.status() == 404 || response.status() == 503) {
//            return new BadRequestException("Employee server is down try later");
//        }

        try (InputStream is = response.body().asInputStream()) {
            ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);

            return new CustomException(errorResponse.getMessage(), errorResponse.getStatus());
        } catch (IOException e) {

            return new CustomException("INTERNAL_ server error");
        }
    }
}
