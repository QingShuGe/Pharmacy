package com.qing.forestpharmacy.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {

    private static final long EXPIRE = 30 * 60 * 1000;
    private static final String SECRET = "!ad#12~";


    /**
     * 生成token
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("username", username)
//                .withClaim("role",role)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))
                .sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * 根据token获取claim
     * @param token
     * @return
     */
    public static String getClaim(String token, String claim) {
        return JWT.decode(token).getClaim(claim).asString();
    }
    /**
     * 校验token
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

}
