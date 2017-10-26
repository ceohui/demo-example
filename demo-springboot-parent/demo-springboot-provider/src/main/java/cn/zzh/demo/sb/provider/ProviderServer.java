package cn.zzh.demo.sb.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderServer {

	public static void main(String[] args) {
		SpringApplication.run(ProviderServer.class, args);
	}
}
