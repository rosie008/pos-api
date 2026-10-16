package com.rose.pos_api.dto.request.auth;

import lombok.Data;

@Data
public class RequestLoginDTO {
    private String username;
    private String password;
}
