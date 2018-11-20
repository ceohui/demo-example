import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class WhoisMain {

	/**
	 * 构建请求
	 *
	 * @param keyword
	 * @param postfix
	 * @return
	 */
	public static HttpRequest buildBaiduRequest(String keyword, String postfix) {
		HttpRequest request = HttpRequest.post("https://cloud.baidu.com/api/bcd/search/status");
		//body: {"domainNames":[{"label":"zzpptt","tld":"com"}]}
		request.body("{\"domainNames\":[{\"label\":\"" + keyword + "\",\"tld\":\"" + postfix + "\"}]}");
		return request;
	}

	public static HttpRequest buildAliyunRequest(String keyword, String postfix) {
		HttpRequest request = HttpRequest.post("https://checkapi.aliyun.com/check/checkdomain?domain=" + keyword + "." +
				postfix + "&command=&token=Yd57bf2900b050a8d7cb6713990f93823&ua=&currency=&site=&bid=&_csrf_token=");
		return request;
	}


	public static HashSet<String> genDomainByLetter() {

		HashSet<String> set = new HashSet<String>();

		for (int i = '0'; i <= '9'; i++) {
			for (int i2 = '0'; i2 <= '9'; i2++) {
				for (int i3 = '0'; i3 <= '9'; i3++) {
					for (int i4 = '0'; i4 <= '9'; i4++) {
						set.add(String.format("%c%c%c%c", (char) i, (char) i2, (char) i3, (char) i4));
//						if (set.size() > 10) {
//							break;
//						}
					}
				}
			}
		}

		System.out.println("size:" + set.size());
//		System.out.println("set:" + set);
		return set;
	}

	public static void writeToFile(String ret) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\data\\logs\\domain.txt"));
		writer.newLine();
		writer.write(ret);

		writer.flush();
		writer.close();
	}

	public static JSONObject queryDomainByBaidu(String keyword, String postfix) throws IOException {
		HttpResponse resp = buildBaiduRequest(keyword, postfix).execute();
		// {"success":true,"status":200,"result":{"accurate":[{"domainName":"abc.com","status":"REGISTERED","displayLevel":"ACCURATE"}],"common":[],"recommend":[],"others":[]}}
		System.out.println(resp.body());

		JSONObject body = JSONObject.parseObject(resp.body());
		if (body.getIntValue("status") != 200) {
			System.out.println("fail|" + resp.body());
			return null;
		}

		JSONObject domainResp = body.getJSONObject("result").getJSONArray("accurate").getJSONObject(0);
		if ("UNREGISTERED".equals(domainResp.getString("status"))) {
			System.out.println("=============>NB:" + domainResp.toJSONString());
			writeToFile(domainResp.toJSONString());
			return domainResp;
		}
		return null;
	}

	public static JSONObject queryDomainByAliyun(String keyword, String postfix) throws IOException {
		HttpResponse resp = buildAliyunRequest(keyword, postfix).execute();
		// {"errorCode":0,"module":[{"avail":0,"name":"abc.com","tld":"com"}],"success":"true"}
		System.out.println(resp.body());

		JSONObject body = JSONObject.parseObject(resp.body());
		if (body.getIntValue("errorCode") != 0) {
			System.out.println("fail|" + resp.body());
			return null;
		}

		JSONObject domainResp = body.getJSONArray("module").getJSONObject(0);
		if (1 == domainResp.getIntValue("avail")) {
			System.out.println("=============>NB:" + domainResp.toJSONString());
			writeToFile(domainResp.toJSONString());
			return domainResp;
		}
		return null;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {

//		HttpRequest request = HttpRequest.post("https://cloud.baidu.com/api/bcd/search/status");
//		request.body("{\"domainNames\":[{\"label\":\"zzpptt\",\"tld\":\"com\"}]}");
//		HttpResponse response = request.execute();
//		System.out.println("demo:" + response.body());

		List<String> validDomainList = new ArrayList<>();

		ExecutorService executor = Executors.newFixedThreadPool(500, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});

		HashSet<String> letterSet = genDomainByLetter();
		final CountDownLatch latch = new CountDownLatch(letterSet.size());
		for (String str : letterSet) {

			final String domainStr = str;

			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						JSONObject ret = queryDomainByAliyun(domainStr, "com");

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			});

//			if (ret != null && ret.get() != null) {
//				validDomainList.add(ret.get().toJSONString());
//			}
		}

		latch.await();
//		Thread.sleep(100);

		System.out.println("===============================================");
		System.out.println("validDomainList size:" + validDomainList.size());
		for (String str : validDomainList) {
			System.out.println(str);
		}


	}
}
