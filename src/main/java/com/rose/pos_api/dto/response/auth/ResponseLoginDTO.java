package com.rose.pos_api.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseLoginDTO {
    String accessToken;
    String tokenType;
    long expiresIn;
}
