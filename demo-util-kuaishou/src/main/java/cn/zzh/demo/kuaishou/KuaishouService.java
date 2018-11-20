package cn.zzh.demo.kuaishou;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.util.*;

public class KuaishouService {

	private static String domain = "http://api.gifshow.com";

	URLCodec urlCodec = new URLCodec(Charsets.UTF_8.name());

	public JSONObject buildCommonParams() {
		JSONObject params = new JSONObject();
		params.put("appver", "5.5.4.376");// app版本号
		params.put("did", "FD1EA6E1-FCAF-4230-8B52-48F55B5C3806");// 设备id
		params.put("c", "a");
		params.put("ver", "5.5");
		params.put("ud", "656253016");// uid
		params.put("lon", "118.6879137210396");// 经度
		params.put("lat", "25.36196364858262");// 纬度
		params.put("sys", "ios11.0.1");// 系统版本
		params.put("mod", "iPhone9,1");// 设备型号
		params.put("net", "中国联通_5");// 网络

		return params;
	}

	public String sign(List<String> params) {
		Collections.sort(params);


		return null;
	}

	public void getUserInfo() throws EncoderException {
		// http://api.ksapisrv.com/rest/n/user/profile/v2?appver=5.5.4.376&did=FD1EA6E1-FCAF-4230-8B52-48F55B5C3806&c=a&
		// ver=5.5&ud=656253016&lon=118.6879137210396&lat=25.36196364858262&sys=ios11.0.1&mod=iPhone9%2C1&net=%E4%B8%AD%E5%9B%BD%E8%81%94%E9%80%9A_5
		StringBuilder builder = new StringBuilder();
		builder.append(domain).append("/rest/n/user/profile/v2");

		JSONObject params = buildCommonParams();
		params.put("ud", "656253016");

		params.put("__NStokensig", "a67ad46e7122322f0a208af38c064e4e9d99c1622ce19f65446f70e6eb092555");
		params.put("client_key", "56c3713c");
		params.put("country_code", "cn");
		params.put("language", "zh-Hans-CN%3Bq%3D1%2C%20en-CN%3Bq%3D0.9");
		params.put("sig", "544edbde415e34f8bd404468a1deb8be");
		params.put("token", "dab36756d2ad4e939d68185130ac50f1-656253016");
		params.put("user", "656253016");

		List<String> kvParams = new ArrayList<>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			kvParams.add(Joiner.on("=").join(param.getKey(), param.getValue()));
		}

		HttpRequest req = HttpRequest.get(builder.append("?").append(Joiner.on("&").join(kvParams)).toString());

		// 构建请求
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "kwai-ios");
//		headers.put("X-REQUESTID","2648069482");

		// =&=&=cn&=zh-Hans-CN%3Bq%3D1%2C%20en-CN%3Bq%3D0.9&sig=&=&user=
//		Map<String, Object> formMap = new HashMap<>();
//		formMap.put("__NStokensig","a67ad46e7122322f0a208af38c064e4e9d99c1622ce19f65446f70e6eb092555");
//		formMap.put("client_key","56c3713c");
//		formMap.put("country_code","cn");
//		formMap.put("language","zh-Hans-CN;q=1, en-CN;q=0.9");
//		formMap.put("sign","544edbde415e34f8bd404468a1deb8be");
//		formMap.put("token","dab36756d2ad4e939d68185130ac50f1-656253016");
//		formMap.put("user","656253016");


//		System.out.println("url:"+builder.toString());
//		HttpRequest req = HttpRequest.post(builder.toString());
//		req.addHeaders(headers);
//		req.keepAlive(true);
//		req.form(formMap);
		//
		HttpResponse resp = req.execute();
		System.out.println(resp.body());
	}

	public void getFans() {

	}

	public static void main(String[] args) {
		String url = "http://api.gifshow.com/rest/n/user/profile/v2?appver=5.5.4.376&did=FD1EA6E1-FCAF-4230-8B52-48F55B5C3806&c=a&ver=5.5&ud=656253016&lon=118.6877790001395&lat=25.36186094398177&sys=ios11.0.1&mod=iPhone9,1&net=%E4%B8%AD%E5%9B%BD%E8%81%94%E9%80%9A_5&__NStokensig=a67ad46e7122322f0a208af38c064e4e9d99c1622ce19f65446f70e6eb092555&client_key=56c3713c&country_code=cn&language=zh-Hans-CN%3Bq%3D1%2C%20en-CN%3Bq%3D0.9&sig=544edbde415e34f8bd404468a1deb8be&token=dab36756d2ad4e939d68185130ac50f1-656253016&user=656253016";

		String uri = "http://api.gifshow.com/rest/n/user/profile/v2";

		Map<String, Object> params = new HashMap<>();
		params.put("appver", "5.5.4.376");
		params.put("did", "FD1EA6E1-FCAF-4230-8B52-48F55B5C3806");
		params.put("c", "a");
		params.put("ver", "5.5");
		params.put("ud", "656253016");
		params.put("lon", "118.6877790001395");
		params.put("lat", "25.36186094398177");
		params.put("sys", "ios11.0.1");
		params.put("mod", "iPhone9,1");
//		params.put("net", "中国联通_5");
		params.put("net", "%E4%B8%AD%E5%9B%BD%E8%81%94%E9%80%9A_5");

//		params.put("__NStokensig","a67ad46e7122322f0a208af38c064e4e9d99c1622ce19f65446f70e6eb092555");
		params.put("client_key", "56c3713c");
		params.put("country_code", "cn");
//		params.put("language", "zh-Hans-CN;q=1, en-CN;q=0.9");
		params.put("language", "zh-Hans-CN%3Bq%3D1%2C%20en-CN%3Bq%3D0.9");
		params.put("token", "dab36756d2ad4e939d68185130ac50f1-656253016");
		params.put("user", "656253016");
//		params.put("sig","544edbde415e34f8bd404468a1deb8be");

		StringBuilder builder = new StringBuilder();
		List<String> kvParams = new ArrayList<>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			kvParams.add(Joiner.on("=").join(param.getKey(), param.getValue()));
		}
		Collections.sort(kvParams);

//		String sign = EncryptUtil.sign(EncryptUtil.loadRsaPrivateKey("56c3713c"), Joiner.on("&").join(kvParams));
//		System.out.println("sign:" + sign);

		String pwd = Joiner.on("").join(kvParams);
		String sign = Hashing.md5().newHasher().putString(pwd + "23caab00356c", Charsets.UTF_8).hash().toString();
		System.out.println(pwd);
		System.out.println("sign:" + sign);
		System.out.println("544edbde415e34f8bd404468a1deb8be".equalsIgnoreCase(sign));
		String x = "c=aclient_key=56c3713ccontent=qqdid=AE4D455F-534B-46D1-81C8-DDF186C2314Flat=39.995543lon=116.474270mod=iPad4,4net=5photo_id=254310485refer=ks://photo/91405485/254310485/3/1_a/1503560554377355265_h0#addcommentsys=ios7.1.2token=efa9a4a370c14f56bb6f2183fb5088d8-94853995ud=94853995user_id=91405485ver=4.6123caab00356c";
		System.out.println("sign2:" + Hashing.md5().newHasher().putString(x, Charsets.UTF_8).hash().toString());


		kvParams.add("sig=544edbde415e34f8bd404468a1deb8be");
		HttpRequest req = HttpRequest.get(uri + "?" + Joiner.on("&").join(kvParams));
//		req.form(params);

		HttpResponse resp = req.execute();
		System.out.println(resp.body().toString());
	}

}
