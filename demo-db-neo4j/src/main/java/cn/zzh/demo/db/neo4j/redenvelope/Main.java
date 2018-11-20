package cn.zzh.demo.db.neo4j.redenvelope;

import com.alibaba.fastjson.JSON;
import org.neo4j.driver.v1.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.neo4j.driver.v1.Values.parameters;


public class Main {

	static Map<Long, User> userMap = new HashMap<>();
	static Map<Long, RedEnvelope> redMap = new HashMap<>();

	public static Driver getDriver() {
		Driver driver = GraphDatabase.driver("bolt://localhost:32768", AuthTokens.basic("neo4j", "123456"));
		return driver;
	}


	public static void main(String[] args) throws IOException {

		loadRedEnvelopeData();

		System.out.println("start");
		Driver driver = getDriver();
		Session session = driver.session();

		Main main = new Main();
		main.deleteAll(session);
		main.runRed(session);
		main.printAll(session);

		session.close();
		driver.close();
	}


	public static void loadRedEnvelopeData() throws IOException {

		String dir = "D:\\工作\\功能-2018\\20181024-红包验证码\\red_transfer\\";

		String file = dir + "20181111_event.log";
		BufferedReader reader = null;
		String line = null;


//		reader = new BufferedReader(new FileReader(file));
//		while ((line = reader.readLine()) != null) {
//			String[] params = line.split("\t");
//			long userId = Long.valueOf(params[0]);
//
//			User user = userMap.get(userId);
//			if (user == null) {
//				user = new User();
//				user.setUserId(userId);
//
//				userMap.put(userId, user);
//			}
//			user.getRelationDevice().add(params[1]);
//			user.getRelationIp().add(params[2]);
//		}
//		reader.close();

		file = dir + "20181112_red.log";
		reader = new BufferedReader(new FileReader(file));
		line = null;


		while ((line = reader.readLine()) != null) {
			// r.id,r.sender_id,r.sum_money,r.red_envelope_count,record.receiver_user_id, record.sum_money
			// 2702978464282522683	2678781720982931500	100	50	2686180525530961452	4
			String[] params = line.split("\t");
			long redId = Long.valueOf(params[0]);
			long sendUserId = Long.valueOf(params[1]);
			int money = Integer.valueOf(params[2]);
			int count = Integer.valueOf(params[3]);
			long receiveUserId = Long.valueOf(params[4]);
			int receiveMoney = Integer.valueOf(params[5]);

			RedEnvelope red = redMap.get(redId);
			if (red == null) {
				red = new RedEnvelope();
				red.setRedId(redId);
				red.setMoney(money);
				red.setCount(count);
				red.setSendUser(getUser(sendUserId));

				redMap.put(redId, red);
			}
			red.getReviceUser().add(getUser(receiveUserId));
		}
		reader.close();
	}

	public static User getUser(Long userId) {
		User user = userMap.get(userId);
		if (user == null) {
			user = new User();
			user.setUserId(userId);
		}
		return user;
	}

	public void runRed(final Session session) {
		int n = 0;

		ExecutorService service = Executors.newFixedThreadPool(100);

		for (final Map.Entry<Long, RedEnvelope> entry : redMap.entrySet()) {

			final int i = n++;
//			service.submit(new Runnable() {
//				@Override
//				public void run() {
					long now = System.currentTimeMillis();

					long redId = entry.getKey();

					if (queryNodeRed(session, redId) == null) {
						saveNodeRed(session, entry.getValue());
					}

					User sendUser = entry.getValue().getSendUser();
					if (queryNodeUser(session, sendUser.getUserId()) == null) {
						saveNodeUser(session, sendUser);
					}
					saveRelationUserSendRed(session, sendUser.getUserId(), redId);

					for (User reviceUser : entry.getValue().getReviceUser()) {
						if (queryNodeUser(session, reviceUser.getUserId()) == null) {
							saveNodeUser(session, reviceUser);
						}
						saveRelationUserReceiveRed(session, reviceUser.getUserId(), redId);
					}
					System.out.printf("i:%s \t runRed|red:%s cost:%s \n", i, redId, System.currentTimeMillis() - now);
//				}
//			});

		}


	}


	public void runDemo(Session session) {
		User u1 = new User();
		u1.setUserId(123);
//		u1.setRelationIp(HashSet. .asList("1.1.1.1", "2.2.2.2"));
//		u1.setRelationDevice(Arrays.asList("d1", "d2"));

		User user = queryNodeUser(session, u1.getUserId());
		if (user == null) {
			saveNodeUser(session, u1);
		}

		for (String deviceId : u1.getRelationDevice()) {
			Device device = queryNodeDevice(session, deviceId);
			if (device == null) {
				device = new Device();
				device.setDeviceId(deviceId);
				saveNodeDevice(session, device);
			}
			saveRelationUserToDevice(session, u1.getUserId(), deviceId);
		}

		for (String ip : u1.getRelationIp()) {
			Ip ipNode = queryNodeIp(session, ip);
			if (ipNode == null) {
				ipNode = new Ip();
				ipNode.setIp(ip);
				saveNodeIp(session, ipNode);
			}
			saveRelationUserToIp(session, u1.getUserId(), ip);
		}
	}

	public void printAll(Session session) {
		System.out.println("=======> print all");
		StatementResult result = session.run("MATCH (n) WHERE n:User RETURN n.userId AS userId");
		while (result.hasNext()) {
			Record record = result.next();
			System.out.printf("user|uid:%s\n", record.get("userId").asLong());
		}

		result = session.run("MATCH (n) WHERE n:Device RETURN n.deviceId AS deviceId");
		while (result.hasNext()) {
			Record record = result.next();
			System.out.printf("device|did:%s\n", record.get("deviceId").asString());
		}

		result = session.run("MATCH p = (a)-[r]->(b) return p");
		while (result.hasNext()) {
			Record record = result.next();
			System.out.println("relation:" + JSON.toJSONString(record.asMap()));
		}
	}

	public void deleteAll(Session session) {
		StatementResult result = session.run("MATCH (n) DETACH DELETE n");
	}

	public RedEnvelope queryNodeRed(Session session, long redId) {
		StatementResult result = session.run("MATCH (x:RedEnvelope) WHERE x.redId={redId} RETURN x.redId AS redId,x.money AS money, x.count AS count,x.sendUserId AS sendUserId",
				parameters("redId", redId));
		if (result.hasNext()) {
			Record record = result.next();
			RedEnvelope red = new RedEnvelope();
			red.setRedId(record.get("redId").asLong());
			red.setMoney(record.get("money").asInt());
			red.setCount(record.get("count").asInt());

			return red;
		}
		return null;
	}

	public StatementResult saveNodeRed(Session session, RedEnvelope red) {
		return session.run("create (x:RedEnvelope{redId:{redId},sendUserId:{sendUserId},money:{money},count:{count}})",
				parameters("redId", red.getRedId(), "sendUserId", red.getSendUser().getUserId(), "money", red.getMoney(), "count", red.getCount()));
	}


	public User queryNodeUser(Session session, long userId) {
		StatementResult result = session.run("MATCH (u:User) WHERE u.userId={userId} RETURN u", parameters("userId", userId));
		if (result.hasNext()) {
			return new User();
		}
		return null;
	}

	public StatementResult saveNodeUser(Session session, User user) {
		session.run("create (user:User{userId:{userId}})", parameters("userId", user.getUserId()));

		for (String deviceId : user.getRelationDevice()) {
			Device device = queryNodeDevice(session, deviceId);
			if (device == null) {
				device = new Device();
				device.setDeviceId(deviceId);
				saveNodeDevice(session, device);
			}
			saveRelationUserToDevice(session, user.getUserId(), deviceId);
		}

		for (String ip : user.getRelationIp()) {
			Ip ipNode = queryNodeIp(session, ip);
			if (ipNode == null) {
				ipNode = new Ip();
				ipNode.setIp(ip);
				saveNodeIp(session, ipNode);
			}
			saveRelationUserToIp(session, user.getUserId(), ip);
		}

		return null;
	}

	public Device queryNodeDevice(Session session, String deviceId) {
		StatementResult result = session.run("MATCH (x:Device) WHERE x.deviceId={deviceId} RETURN x", parameters("deviceId", deviceId));
		if (result.hasNext()) {
			return new Device();
		}
		return null;
	}

	public StatementResult saveNodeDevice(Session session, Device device) {
		return session.run("CREATE (x:Device {deviceId:{deviceId}})", parameters("deviceId", device.getDeviceId()));
	}

	public Ip queryNodeIp(Session session, String ip) {
		StatementResult result = session.run("MATCH (x:Ip) WHERE x.ip={ip} RETURN x", parameters("ip", ip));
		if (result.hasNext()) {
			return new Ip();
		}
		return null;
	}

	public StatementResult saveNodeIp(Session session, Ip ip) {
		return session.run("CREATE (x:Ip {ip:{ip}})", parameters("ip", ip.getIp()));
	}

	public StatementResult saveRelationUserToDevice(Session session, long userId, String deviceId) {
		String cmd = "MATCH (u:User),(d:Device) WHERE u.userId={userId} AND d.deviceId={deviceId} CREATE(u)-[r:usedDevice]->(d) return r";
		return session.run(cmd, parameters("userId", userId, "deviceId", deviceId));
	}

	public StatementResult saveRelationUserToIp(Session session, long userId, String ip) {
		String cmd = "MATCH (u:User),(d:Ip) WHERE u.userId={userId} AND d.ip={ip} CREATE(u)-[r:usedIp]->(d) return r";
		return session.run(cmd, parameters("userId", userId, "ip", ip));
	}

	public StatementResult saveRelationUserSendRed(Session session, long userId, long redId) {
		String cmd = "MATCH (a:User),(b:RedEnvelope) WHERE a.userId={userId} AND b.redId={redId} CREATE(a)-[r:send]->(b) return r";
		return session.run(cmd, parameters("userId", userId, "redId", redId));
	}

	public StatementResult saveRelationUserReceiveRed(Session session, long userId, long redId) {
		String cmd = "MATCH (a:User),(b:RedEnvelope) WHERE a.userId={userId} AND b.redId={redId} CREATE(a)-[r:grab]->(b) return r";
		return session.run(cmd, parameters("userId", userId, "redId", redId));
	}

}
