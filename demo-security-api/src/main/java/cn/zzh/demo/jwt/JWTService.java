package cn.zzh.demo.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class JWTService {


	public static void main(String[] args) throws UnsupportedEncodingException {
		long now = System.currentTimeMillis();
		Algorithm algorithmHS = Algorithm.HMAC256("secret");
		String token = JWT.create()
				.withIssuer("victorzheng")
				.withSubject("用户")
				.withClaim("uid", 10086)
				.withClaim("name", "用户昵称")
				.withExpiresAt(new Date(now + 3000))
				.sign(algorithmHS);


		System.out.println(token);

		// verify token
		JWTVerifier verifier = JWT.require(algorithmHS).withIssuer("victorzheng").build();
		try {
			DecodedJWT jwt = verifier.verify(token);
//			System.out.println();
			System.out.println("uid:" + jwt.getClaim("uid").asInt());
			System.out.println("header:" + new String(Base64.getDecoder().decode(jwt.getHeader())));
			System.out.println("payload:" + new String(Base64.getDecoder().decode(jwt.getPayload())));
			System.out.println(JSON.toJSONString(jwt));
		} catch (TokenExpiredException e) {
			System.out.println("过期|" + e.getMessage());
		}
	}
}
