package cn.zzh.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceClientMain {

	public static void main(String[] args) {

		RedisClient client = RedisClient.create(RedisURI.Builder.redis("172.17.6.232", 6379).build());

		StatefulRedisConnection<String, String> connection = client.connect();

		RedisCommands<String, String> commands = connection.sync();

		String setRet = commands.set("lettuce-test", "hello world");
		String getRet = commands.get("zzh");

		System.out.println(setRet + " , " + getRet);

		connection.close();

		client.shutdown();
	}
}
