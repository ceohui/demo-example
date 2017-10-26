package cn.zzh.demo.sb.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="spring-cloud-provider")
public interface UserService {

	@RequestMapping("user/hello")
	public String sayHello(@RequestParam("name") String name);
}
