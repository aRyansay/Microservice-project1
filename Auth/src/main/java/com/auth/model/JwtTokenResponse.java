package com.auth.model;

import lombok.Data;

@Data
public class JwtTokenResponse {
    private String token;
    private String type;
    private String validatity;

}
