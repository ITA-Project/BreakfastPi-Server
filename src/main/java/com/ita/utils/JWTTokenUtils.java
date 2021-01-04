package com.ita.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ita.domain.entity.User;

import java.util.Date;

public class JWTTokenUtils {

    public final static String AUTHORIZATION = "Authorization";
    public final static String BEARER = "Bearer";
    private final static String secret = "BA125SDSR62WQEQ23126";

    public static String getUserToken(User user) {
        return JWT.create()
                .withKeyId(user.getId().toString())
                .withAudience(user.getId().toString())
                .withClaim("userId", user.getId())
                .withClaim("time", new Date())
                .sign(Algorithm.HMAC256(secret));
    }

}
