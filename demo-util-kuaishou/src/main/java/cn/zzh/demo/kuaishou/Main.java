package cn.zzh.demo.kuaishou;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.apache.commons.codec.EncoderException;

public class Main {

	public static void main(String[] args) throws EncoderException {

		KuaishouService ksService = new KuaishouService();
//		ksService.getUserInfo();

		HttpRequest req = HttpRequest.get("https://www.kuaishou.com/live/user/57669808");
		HttpResponse resp = req.execute();
		System.out.println(resp.body());
	}

}
