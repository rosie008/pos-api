package com.rose.pos_api.statval.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public final class PosConstants {

    public static final String JWT_ISSUER = "pos-auth";
    public static final String JWT_AUDIENCE = "pos-api";
    public static final long JWT_EXPIRATION =  15 * 60 * 1000;


}
