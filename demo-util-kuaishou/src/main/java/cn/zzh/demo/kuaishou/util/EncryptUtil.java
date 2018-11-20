package cn.zzh.demo.kuaishou.util;

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class EncryptUtil {

	public static RSAPrivateKey loadRsaPrivateKey(String privateKey) {
		byte[] privateKeyBytes = Base64.decodeBase64(privateKey);
		try {
			return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(
					new PKCS8EncodedKeySpec(privateKeyBytes));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String sign(RSAPrivateKey rsaPrivateKey, String params) {
		try {
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(rsaPrivateKey);
			signature.update(params.getBytes(Charsets.UTF_8));
			URLCodec urlCodec = new URLCodec(Charsets.UTF_8.name());
			return urlCodec.encode(Base64.encodeBase64String(signature.sign()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
