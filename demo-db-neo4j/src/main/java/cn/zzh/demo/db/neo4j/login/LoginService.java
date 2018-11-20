package cn.zzh.demo.db.neo4j.login;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class LoginService implements AutoCloseable {

	private Driver driver;

	public void init() {
		//driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		driver = GraphDatabase.driver("bolt://localhost:32768", AuthTokens.basic("neo4j", "123456"));
	}

	@Override
	public void close() throws Exception {
		driver.close();
	}


	/**
	 * 创建唯一约束
	 */
	public void createUnique() {
		try (Session session = driver.session()) {
			session.run("CREATE CONSTRAINT ON (u:User) ASSERT u.id IS UNIQUE");
		}
	}

	public void delUser() {
		try (Session session = driver.session()) {
			session.run("MATCH(u:User) WHERE u.id=1 DELETE u");
		}
	}

	public void delDevice(String deviceId) {
		try (Session session = driver.session()) {
			session.run("MATCH(d:Device) WHERE d.deviceId={deviceId} DELETE d", parameters("deviceId", deviceId));
		}
	}

	public void delRelation(long userId, String deviceId) {
		try (Session session = driver.session()) {
			session.run("MATCH(u:User)-[r:login]-(d:Device) WHERE u.userId={userId},d.deviceId={deviceId} DELETE r",
					parameters("userId", userId, "deviceId", deviceId));
		}
	}

	public void queryUser() {
		try (Session session = driver.session()) {
			StatementResult ret = session.run("MATCH(nx:User) RETURN nx.id,nx.userId as userId");
			while (ret.hasNext()) {
				Record record = ret.next();
				System.out.println(record.asMap());
			}
		}
	}

	public void queryDevice() {
		try (Session session = driver.session()) {
			StatementResult ret = session.run("MATCH(d:Device) RETURN d.id, d.deviceId");
			while (ret.hasNext()) {
				Record record = ret.next();
				System.out.println(record.asMap());
			}
		}
	}

	public void login(User u, Device d) {
		final String message = "helloworld";
		try (Session session = driver.session()) {
			String greeting = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run("CREATE (a:Greeting) " +
									"SET a.message = $message " +
									"RETURN a.message + ', from node ' + id(a)",
							parameters("message", message));
					return result.single().get(0).asString();
				}
			});
			System.out.println(greeting);
		}
	}

	public static void main(String[] args) {
		LoginService loginService = new LoginService();
		loginService.init();


		loginService.delDevice("1001");

		loginService.queryUser();
		loginService.queryDevice();
	}


}
