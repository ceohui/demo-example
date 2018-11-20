package cn.zzh.demo.db.neo4j.login;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;


public class Neo4jMain {


	public static Driver getDriver() {
		Driver driver = GraphDatabase.driver("bolt://localhost:32768", AuthTokens.basic("neo4j", "123456"));
		return driver;
	}


	public static void main(String[] args) {

		System.out.println("start");

		Driver driver = getDriver();
		Session session = driver.session();

		session.run("CREATE (a:Person {name: {name}, title: {title}})", parameters("name", "Arthur", "title", "King"));

		StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
				"RETURN a.name AS name, a.title AS title", parameters("name", "Arthur"));
		while (result.hasNext()) {
			Record record = result.next();
			System.out.println(record.get("title").asString() + " " + record.get("name").asString());
		}

		session.close();
		driver.close();
	}

	public static void importLoginDevice() {


		Driver driver = GraphDatabase.driver("bolt://localhost:32768", AuthTokens.basic("neo4j", "123456"));
		Session session = driver.session();

		// 设备


		session.run("CREATE (a:Person {name: {name}, title: {title}})", parameters("name", "Arthur", "title", "King"));

		StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
				"RETURN a.name AS name, a.title AS title", parameters("name", "Arthur"));
		while (result.hasNext()) {
			Record record = result.next();
			System.out.println(record.get("title").asString() + " " + record.get("name").asString());
		}

		session.close();
		driver.close();

	}
}
